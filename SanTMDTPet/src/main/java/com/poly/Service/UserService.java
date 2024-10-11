package com.poly.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.poly.Model.User;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.dto.UserDTO;

public interface UserService {
	boolean register(RegisterDTO registerDTO);

	boolean login(LoginDTO loginDTO);

	int countTotalCustomers();

	List<User> getAllUsers();

	List<User> getUsersByGender(Boolean gender);

	User getUserByUsername(String username);

	void saveOrUpdateUser(User user);

	void deleteUser(Integer usersId);

	int getTotalUsers();

	int getTotalProducts();

	// Cập nhật vai trò người dùng thành seller
	void updateUserRoleToSeller(int userId);

	// Cập nhật người dùng
	void updateUser(UserDTO userDTO); // Thêm phương thức này vào interface
	
	// Tìm kiếm khách hàng theo tên hoặc username với phân trang
    Page<User> searchUsers(String keyword, Pageable pageable);
}
