package com.poly.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.poly.Service.API.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    JwtAuthFilter jwtAuthFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        // Cho phép các yêu cầu API đăng nhập và các tài nguyên công cộng

                        .requestMatchers("/api/user/login","/api/seller/**", "/api/home/**", "/home/**", "/api/home/images/**","/api/user/user-info").permitAll()
                        // Bảo vệ các endpoint API cho người dùng đã đăng nhập
                         .requestMatchers("/api/admin/**").hasRole("ADMIN")
//                         .requestMatchers("/api/seller/**","/api/user/**").hasRole("SELLER")
                         .requestMatchers("/api/user/**").hasRole("USER")

                        .requestMatchers("/api/user/login", "/api/home/**", "/home/**", "/api/home/images/**",
                                "/api/pet/**", "/api/user/**")
                        .permitAll()
                        // Bảo vệ các endpoint API cho người dùng đã đăng nhập
                        // .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // .requestMatchers("/api/seller/**").hasRole("SELLER")
                        // .requestMatchers("/api/user/**").hasRole("USER")

                        .anyRequest().authenticated() // Các yêu cầu khác phải được xác thực
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class) // Thêm filter JWT trước
                                                                                            // filter
                                                                                            // UsernamePasswordAuthentication
                // .httpBasic(Customizer.withDefaults()) // Sử dụng xác thực HTTP Basic cho các
                // endpoint đã được bảo vệ
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
