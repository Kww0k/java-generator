package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 添加映射路径  
        registry.addMapping("/**")
                // 明确列出允许的域名
                .allowedOrigins("http://localhost:5173") // 替换为实际的域名
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE") // 允许的方法
                .allowedHeaders("*") // 允许的头部设置
                .allowCredentials(true) // 是否发送cookie
                .maxAge(168000); // 预检间隔时间
    }
}
