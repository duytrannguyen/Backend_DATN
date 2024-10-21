package com.poly.component;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.poly.Model.User;
import com.poly.Reponsitory.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
		User user = userRepository.findByEmail(username);
		if (user != null) {
			httpSession.setAttribute("user", user); // Lưu đối tượng user vào session
		}
		System.out.println(user.getUsername());
		System.out.println(user.getRoleId().getRoleName());
	}
}
