package com.poly.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.model.User;
import com.poly.repository.UserRepository;
import com.poly.service.ChangePasswordService;

@Controller
@RequestMapping("home")
public class ChangePasswordController {

    @Autowired
    private ChangePasswordService passwordChangeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login/changePassword")
    public String showChangePasswordForm() {
        return "login/changePassword";
    }

	

    @PostMapping("/login/changePassword")
    public ResponseEntity<String> changePassword(@RequestParam("currentPassword") String currentPassword,
                                                 @RequestParam("newPassword") String newPassword) {
        // Get the currently authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Find the user by username
        User user = userRepository.findByUsername(username);

        // Verify the current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Current password is incorrect");
        }

        // Update the password
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }
}
