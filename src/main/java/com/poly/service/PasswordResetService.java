package com.poly.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org .springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.poly.model.User;
import com.poly.repository.UserRepository;

@Service
public class PasswordResetService {
	 @Autowired
	    private UserRepository userRepository;

	    @Autowired
	    private JavaMailSender emailSender;

	    
	    private Map<String, String> otpMap = new HashMap<>();
	    private Map<String, Long> otpExpirationMap = new HashMap<>();

	    public void sendPasswordResetOTP(String email) {
	        User user = userRepository.findByEmail(email);
	        if (user == null) {
	            throw new RuntimeException("Email not found");
	        }

	        String otp = generateOTP();
	        otpMap.put(email, otp);
	        otpExpirationMap.put(email, System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5));

	        sendOTPEmail(email, otp);
	    }

	    public boolean verifyOTP(String email, String otp) {
	        if (!otpMap.containsKey(email) || !otpMap.get(email).equals(otp)) {
	            return false;
	        }

	        long expirationTime = otpExpirationMap.get(email);
	        if (System.currentTimeMillis() > expirationTime) {
	            otpMap.remove(email);
	            otpExpirationMap.remove(email);
	            return false;
	        }

	        otpMap.remove(email);
	        otpExpirationMap.remove(email);
	        return true;
	    }

	    private String generateOTP() {
	        return String.format("%06d", new Random().nextInt(900000) + 100000);
	    }

	    private void sendOTPEmail(String email, String otp) {
	        SimpleMailMessage message = new SimpleMailMessage();
	        message.setFrom("duytrannguyen122@gmail.com");
	        message.setTo(email);
	        message.setSubject("Password Reset OTP");
	        message.setText("Your password reset OTP is: " + otp);
	        emailSender.send(message);
	    }
	}