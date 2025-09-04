package com.smartcabinet.controller;

import com.smartcabinet.common.result.Result;
import com.smartcabinet.entity.User;
import com.smartcabinet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
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
@Validated
public class UserController {
    
    private final UserService userService;
    
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<User> register(@Valid @RequestBody User user) {
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
        // TODO: 从token中获取用户ID，然后查询用户信息
        return Result.success(null);
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
}
