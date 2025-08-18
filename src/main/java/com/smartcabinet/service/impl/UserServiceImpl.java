package com.smartcabinet.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.smartcabinet.entity.User;
import com.smartcabinet.mapper.UserMapper;
import com.smartcabinet.service.UserService;
import com.smartcabinet.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 用户服务实现类
 * 
 * @author SmartCabinet Team
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(User user) {
        // 检查手机号是否已注册
        User existUser = getByPhone(user.getPhone());
        if (existUser != null) {
            throw new RuntimeException("手机号已注册");
        }
        
        // 加密密码
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        user.setCreditScore(500); // 默认信用分
        user.setRole("USER");
        user.setStatus(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        save(user);
        log.info("用户注册成功，手机号：{}", user.getPhone());
        return user;
    }
    
    @Override
    public String login(String phone, String password) {
        User user = getByPhone(phone);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (!verifyPassword(password, user.getPasswordHash())) {
            throw new RuntimeException("密码错误");
        }
        
        if (user.getStatus() == 1) {
            throw new RuntimeException("账户已被锁定");
        }
        
        return jwtUtil.generateToken(user.getUserId().toString());
    }
    
    @Override
    public String wechatLogin(String code) {
        // TODO: 实现微信登录逻辑
        // 1. 通过code获取微信用户信息
        // 2. 查询或创建用户
        // 3. 生成JWT令牌
        throw new RuntimeException("微信登录功能待实现");
    }
    
    @Override
    public User getByPhone(String phone) {
        return baseMapper.selectByPhone(phone);
    }
    
    @Override
    public User getByOpenid(String openid) {
        return baseMapper.selectByOpenid(openid);
    }
    
    @Override
    public boolean updateUserInfo(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return updateById(user);
    }
    
    @Override
    public boolean updateCreditScore(Long userId, Integer score) {
        return baseMapper.updateCreditScore(userId, score) > 0;
    }
    
    @Override
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
    
    @Override
    public String refreshToken(String refreshToken) {
        if (jwtUtil.validateToken(refreshToken)) {
            String userId = jwtUtil.getUserIdFromToken(refreshToken);
            return jwtUtil.generateToken(userId);
        }
        throw new RuntimeException("刷新令牌无效");
    }
}
