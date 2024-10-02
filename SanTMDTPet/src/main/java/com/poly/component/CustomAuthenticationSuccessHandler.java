package com.poly.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.poly.Model.User;
import com.poly.Service.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	private HttpSession httpSession;
	@Autowired
	UserRepository userRepository;
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException {
	    // Lưu đối tượng người dùng vào session
	    String username = authentication.getName();
	    User user = userRepository.findByUsernameApi(username).orElse(null);
	    if (user != null) {
	        httpSession.setAttribute("user", user); // Lưu đối tượng user vào session
	    }

//	    // Chuyển hướng dựa trên quyền
	    String redirectUrl = "/";
	    if (authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
	        redirectUrl = "/admin/"; // Chuyển đến trang admin
	    } else if (authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_SELER"))) {
	        redirectUrl = "/admin/products/list"; // Chuyển đến trang seller
	    } else if (authentication.getAuthorities().stream()
	            .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER"))) {
	        redirectUrl = "/home/index"; // Chuyển đến trang user
	    }
//	    
	    response.sendRedirect(redirectUrl);
	}
}
