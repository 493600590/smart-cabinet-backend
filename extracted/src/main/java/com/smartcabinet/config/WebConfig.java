package com.smartcabinet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web配置类
 * 
 * @author SmartCabinet Team
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 静态资源映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        // Swagger UI
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // TODO: 添加JWT拦截器
        // registry.addInterceptor(new JwtInterceptor())
        //         .addPathPatterns("/**")
        //         .excludePathPatterns("/users/register", "/users/login", "/users/wechat-login");
    }
}
