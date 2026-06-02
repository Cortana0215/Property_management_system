package com.example.propertymanagement.config;

import com.example.propertymanagement.interceptor.AdminInterceptor;
import com.example.propertymanagement.interceptor.UserInterceptor;
import com.example.propertymanagement.interceptor.StaffInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AdminInterceptor adminInterceptor;

    @Autowired
    private UserInterceptor userInterceptor;

    @Autowired
    private StaffInterceptor staffInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Admin routes
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/login", "/admin/login", "/logout", "/css/**", "/images/**", "/uploads/**");

        // Staff routes
        registry.addInterceptor(staffInterceptor)
                .addPathPatterns("/staff/**")
                .excludePathPatterns("/login", "/logout", "/css/**", "/images/**", "/uploads/**");

        // Resident routes
        registry.addInterceptor(userInterceptor)
                .addPathPatterns("/", "/report", "/report-success", "/profile", "/profile/update")
                .excludePathPatterns("/login", "/user/login", "/logout", "/css/**", "/images/**", "/uploads/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}
