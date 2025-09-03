package com.smartcabinet.controller;

import com.smartcabinet.common.result.Result;
import com.smartcabinet.common.result.ResultCode;
import com.smartcabinet.model.entity.User;
import com.smartcabinet.service.UserService;
import com.smartcabinet.utils.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 用户控制器
 * 
 * @author SmartCabinet Team
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户相关接口")
public class UserController {
    
    private final UserService userService;
    private final JwtUtil jwtUtil;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setAvatar(request.getAvatar());
        user.setOpenid(request.getOpenid());
        // 将明文密码临时放入 passwordHash 字段，Service 中会进行加密
        user.setPasswordHash(request.getPassword());

        User registeredUser = userService.register(user);
        return Result.success(registeredUser);
    }
    
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest request) {
        String token = userService.login(request.getPhone(), request.getPassword());
        return Result.success(token);
    }
    
    /**
     * 微信登录
     */
    @PostMapping("/wechat-login")
    public Result<String> wechatLogin(@RequestBody WechatLoginRequest request) {
        String token = userService.wechatLogin(request.getCode());
        return Result.success(token);
    }
    
    /**
     * 获取用户信息
     */
    @GetMapping("/profile")
    public Result<User> getProfile(@RequestHeader("Authorization") String token) {
        try {
            // 去掉 "Bearer " 前缀
            String actualToken = token.replace("Bearer ", "");
            
            // 从token中获取用户ID
            String userIdStr = jwtUtil.getUserIdFromToken(actualToken);
            Long userId = Long.parseLong(userIdStr);
            
            // 查询用户信息
            User user = userService.getById(userId);
            if (user == null) {
                return Result.error(ResultCode.USER_NOT_FOUND);
            }
            
            return Result.success(user);
        } catch (Exception e) {
            return Result.error(ResultCode.INVALID_TOKEN);
        }
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/profile")
    public Result<Boolean> updateProfile(@Valid @RequestBody User user,
                                        @RequestHeader("Authorization") String token) {
        // TODO: 从token中获取用户ID，设置到user对象中
        boolean success = userService.updateUserInfo(user);
        return Result.success(success);
    }
    
    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    public Result<String> refreshToken(@RequestBody RefreshTokenRequest request) {
        String newToken = userService.refreshToken(request.getRefreshToken());
        return Result.success(newToken);
    }
    
    /**
     * 登录请求对象
     */
    public static class LoginRequest {
        @NotBlank(message = "手机号不能为空")
        private String phone;
        
        @NotBlank(message = "密码不能为空")
        private String password;
        
        // getters and setters
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
    
    /**
     * 微信登录请求对象
     */
    public static class WechatLoginRequest {
        @NotBlank(message = "授权码不能为空")
        private String code;
        
        // getters and setters
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
    }
    
    /**
     * 刷新Token请求对象
     */
    public static class RefreshTokenRequest {
        @NotBlank(message = "刷新令牌不能为空")
        private String refreshToken;
        
        // getters and setters
        public String getRefreshToken() { return refreshToken; }
        public void setRefreshToken(String refreshToken) { this.refreshToken = refreshToken; }
    }

    /**
     * 用户注册请求对象（使用明文密码）
     */
    public static class RegisterRequest {
        @NotBlank(message = "用户名不能为空")
        private String username;

        @NotBlank(message = "手机号不能为空")
        private String phone;

        private String email;

        private String avatar;

        private String openid;

        @NotBlank(message = "密码不能为空")
        private String password;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getAvatar() { return avatar; }
        public void setAvatar(String avatar) { this.avatar = avatar; }
        public String getOpenid() { return openid; }
        public void setOpenid(String openid) { this.openid = openid; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
}
