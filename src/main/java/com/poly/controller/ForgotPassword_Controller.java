package com.poly.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.User;
import com.poly.repository.UserRepository;
import com.poly.service.PasswordResetService;

@Controller
@RequestMapping("home")
public class ForgotPassword_Controller {
	@Autowired
	private PasswordResetService forgotPasswordService;

	@Autowired
	private UserRepository userRepository;

//	@GetMapping("/forgot-password")
//	public String showForgotPasswordForm() {
//		return "account/forgotPassword";
//	}

	@GetMapping("/verify-otp")
	public String showOTPForm() {
		return "account/otp";
	}

//	@PostMapping("/forgot-password")
//	public String forgotPassword(@RequestParam("email") String email, Model model) {
//		try {
//			forgotPasswordService.sendPasswordResetOTP(email);
//			model.addAttribute("message", "Password reset OTP sent to your email");
//		} catch (RuntimeException e) {
//			model.addAttribute("message", e.getMessage());
//		}
//		return "account/otp";
//	}

	@PostMapping("/verify-otp")
	public String verifyOTP(@RequestParam("email") String email, @RequestParam("otp") String otp, Model model) {
		if (forgotPasswordService.verifyOTP(email, otp)) {
			model.addAttribute("message", "OTP verified successfully");
		} else {
			model.addAttribute("message", "Invalid OTP");
		}
		return "account/otp";
	}

//	@PostMapping("/change-password")
//	public String changePassword(@RequestParam String email, @RequestParam String newPassword, Model model) {
//		// Change the password here
//		model.addAttribute("message", "Password changed successfully");
//		return "result";
//	}
}
