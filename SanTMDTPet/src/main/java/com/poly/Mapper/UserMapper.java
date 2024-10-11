package com.poly.Mapper;

import com.poly.Model.User;
import com.poly.dto.UserDTO;

public class UserMapper {

	// Chuyển đổi từ User sang UserDTO
	public static UserDTO toDTO(User user) {
		if (user == null) {
			return null;
		}
		return new UserDTO(user.getUsersId(), user.getUsername(), user.getFullName(), user.getPassword(), // Nếu cần
				user.getProfileImage(), user.getBirthDate(), user.getGender(), user.getEmail(), user.getPhone(),
				user.getRoleId().getRoleId(), // Giả sử roleId là số nguyên
				user.getStatus().getStatusId() // Giả sử statusId là số nguyên
		);
	}

	// Chuyển đổi từ UserDTO sang User
	public static User toEntity(UserDTO userDTO) {
		if (userDTO == null) {
			return null;
		}
		User user = new User();
		user.setUsersId(userDTO.getUsersId());
		user.setUsername(userDTO.getUsername());
		user.setFullName(userDTO.getFullName());
		user.setPassword(userDTO.getPassword()); // Nếu cần
		user.setProfileImage(userDTO.getProfileImage());
		user.setBirthDate(userDTO.getBirthDate());
		user.setGender(userDTO.getGender());
		user.setEmail(userDTO.getEmail());
		user.setPhone(userDTO.getPhone());
		// Bạn có thể thêm logic để lấy Role và UserStatus từ ID nếu cần
		return user;
	}
}
