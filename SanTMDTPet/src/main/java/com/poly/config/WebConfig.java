package com.poly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	 @Override
	    public void addCorsMappings(CorsRegistry registry) {
	        registry.addMapping("/api/**") // Cấu hình cho phép tất cả các endpoint
	                .allowedOrigins("http://localhost:3000") // Cho phép yêu cầu từ origin này
	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Các phương thức HTTP cho phép
	                .allowedHeaders("*") // Cho phép tất cả các header
	                .allowCredentials(true); // Cho phép cookie trong yêu cầu
	    }
}
