package com.poly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.model.User;
import com.poly.repository.UserRepository;



@Service
public class ChangePasswordService {
    private final UserRepository usersRepository;

    public ChangePasswordService(UserRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        // Lấy thông tin người dùng từ database
        User user = usersRepository.findByUsername(username);

        // Kiểm tra mật khẩu cũ
        if (!user.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Mật khẩu cũ không chính xác!");
        }

        // Kiểm tra mật khẩu mới
        if (newPassword.isEmpty() || newPassword.length() < 8) {
            throw new IllegalArgumentException("Mật khẩu mới phải dài ít nhất 8 ký tự!");
        }

        // Cập nhật mật khẩu mới
        user.setPassword(newPassword);
        usersRepository.saveAndFlush(user);
    }
}