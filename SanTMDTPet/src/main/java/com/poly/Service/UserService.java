package com.poly.Service;

import java.util.List;

import com.poly.Model.User;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;

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
}
