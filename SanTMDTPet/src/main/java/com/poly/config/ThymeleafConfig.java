//package com.poly.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.ViewResolver;
//import org.thymeleaf.spring6.SpringTemplateEngine;
//import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
//import org.thymeleaf.spring6.view.ThymeleafViewResolver;
//import org.thymeleaf.templatemode.TemplateMode;
//
//@Configuration
//public class ThymeleafConfig {
//
//	@Bean
//	public SpringTemplateEngine templateEngine() {
//		SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//		templateEngine.addTemplateResolver(htmlTemplateResolver());
//		return templateEngine;
//	}
//
//	@Bean
//	public SpringResourceTemplateResolver htmlTemplateResolver() {
//		SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
//		templateResolver.setPrefix("classpath:/templates/");
//		templateResolver.setSuffix(".html");
//		templateResolver.setTemplateMode("HTML");
//		templateResolver.setCharacterEncoding("UTF-8");
//		return templateResolver;
//	}
//
//	@Bean
//	public ViewResolver viewResolver() {
//		ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
//		viewResolver.setTemplateEngine(templateEngine());
//		viewResolver.setCharacterEncoding("UTF-8");
//		return viewResolver;
//	}
//}
