package com.poly.Controller.Seller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Mapper.UserMapper;
import com.poly.Model.User;
import com.poly.Reponsitory.UserRepository;
import com.poly.Service.UserService;
import com.poly.dto.UserDTO;

@RestController
@RequestMapping("api/seller/users")
public class Seller_UserController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	// Lấy danh sách người dùng với roleId là 3 (Users)
	@RequestMapping("/list")
	public List<UserDTO> getAllUsers() {
		List<User> users = userRepository.findAllUsersWithUserRole3();
		return users.stream().map(UserMapper::toDTO).collect(Collectors.toList());
	}

	// Cập nhật người dùng
	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		userDTO.setUsersId(id); // Đặt ID cho DTO trước khi cập nhật
		userService.updateUser(userDTO);
		return ResponseEntity.ok("Cập nhật người dùng thành công!");
	}

	// API tìm kiếm khách hàng theo tên hoặc username
	@GetMapping("/search")
	public Page<User> searchUsers(@RequestParam String keyword, Pageable pageable) {
		return userService.searchUsers(keyword, pageable);
	}
}
