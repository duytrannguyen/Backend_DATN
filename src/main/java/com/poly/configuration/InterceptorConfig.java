//package com.poly.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.poly.component.AuthInterceptor;
//
//@Configuration
//public class InterceptorConfig implements WebMvcConfigurer {
//	@Autowired
//	AuthInterceptor authInterceptor;
//
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		// TODO Auto-generated method stub
//		registry.addInterceptor(authInterceptor).addPathPatterns("/admin/**");
//	}
//}
