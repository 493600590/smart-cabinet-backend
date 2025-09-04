package com.smartcabinet.controller;

import com.smartcabinet.common.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 欢迎页面控制器
 * 
 * @author SmartCabinet Team
 */
@RestController
public class WelcomeController {
    
    @GetMapping("/")
    public Result<Map<String, Object>> welcome() {
        Map<String, Object> data = new HashMap<>();
        data.put("service", "Smart Cabinet Backend API");
        data.put("version", "1.0.0");
        data.put("status", "running");
        data.put("timestamp", LocalDateTime.now());
        data.put("documentation", "/swagger-ui/index.html");
        data.put("health", "/actuator/health");
        
        return Result.success("欢迎使用智能柜后端API服务", data);
    }
    
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("Service is healthy");
    }
}
