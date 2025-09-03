package com.smartcabinet.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.smartcabinet.model.entity.User;

/**
 * 用户服务接口
 * 
 * @author SmartCabinet Team
 */
public interface UserService extends IService<User> {
    
    /**
     * 用户注册
     */
    User register(User user);
    
    /**
     * 用户登录
     */
    String login(String phone, String password);
    
    /**
     * 微信登录
     */
    String wechatLogin(String code);
    
    /**
     * 根据手机号查询用户
     */
    User getByPhone(String phone);
    
    /**
     * 根据微信OpenID查询用户
     */
    User getByOpenid(String openid);
    
    /**
     * 更新用户信息
     */
    boolean updateUserInfo(User user);
    
    /**
     * 更新用户信用分
     */
    boolean updateCreditScore(Long userId, Integer score);
    
    /**
     * 验证用户密码
     */
    boolean verifyPassword(String rawPassword, String encodedPassword);
    
    /**
     * 刷新Token
     */
    String refreshToken(String refreshToken);
}
