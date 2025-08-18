package com.smartcabinet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 无人柜系统启动类
 * 
 * @author SmartCabinet Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
@EnableScheduling
@MapperScan("com.smartcabinet.mapper")
public class SmartCabinetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartCabinetApplication.class, args);
        System.out.println("====================================");
        System.out.println("无人柜系统后端服务启动成功！");
        System.out.println("API文档地址: http://localhost:8080/api/swagger-ui/index.html");
        System.out.println("====================================");
    }
}
