package com.poly.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
        registry.addResourceHandler("/images/**").addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/static/assets/");
        registry.addResourceHandler("/vendor/**").addResourceLocations("classpath:/static/vendor/");
        registry.addResourceHandler("/Image_SP/**").addResourceLocations("classpath:/static/Image_SP/");
        registry.addResourceHandler("/Image_Users/**").addResourceLocations("classpath:/static/Image_Users/");
    }
    
}
