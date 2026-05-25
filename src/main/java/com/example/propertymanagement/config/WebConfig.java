package com.example.propertymanagement.config;

import com.example.propertymanagement.interceptor.AdminInterceptor;
import com.example.propertymanagement.interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Admin routes to intercept
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login", "/admin/logout", "/css/**");

        // User routes to intercept
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/", "/report", "/report-success")
                .excludePathPatterns("/login", "/logout", "/css/**");
    }
}
