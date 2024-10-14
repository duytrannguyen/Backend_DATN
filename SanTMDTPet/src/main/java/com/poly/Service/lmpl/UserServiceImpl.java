package com.poly.Service.lmpl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.Model.Role;
import com.poly.Model.User;
import com.poly.Model.UserStatus;
import com.poly.Reponsitory.RolesRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Reponsitory.UserStatusRepository;
import com.poly.Service.SessionService;
import com.poly.Service.UserService;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RolesRepository rolesRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private SessionService sessionService;
	@Autowired
	UserStatusRepository userStatusRepository;

	@Override
	public boolean register(RegisterDTO registerDTO) {
		if (userRepository.existsByUsername(registerDTO.getUsername())) {
			return false;
		}
		User user = modelMapper.map(registerDTO, User.class);
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRoleId(rolesRepository.findById(2).orElse(null));
		userRepository.save(user);
		return true;
	}

	@Override
	public boolean login(LoginDTO loginDTO) {
		if (!userRepository.existsByUsername(loginDTO.getUsername())) {
			return false;
		}
		User existingUser = userRepository.findByUsername(loginDTO.getUsername());
		if (!encoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
			return false;
		} else {
			sessionService.set("current_account", existingUser);
			sessionService.setTimeOut(1 * 24 * 60 * 60);
			return true;
		}
	}

	@Override
	public int countTotalCustomers() {
		return (int) userRepository.count(); // Assuming count() returns total user count
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public List<User> getUsersByGender(Boolean gender) {
		return userRepository.findAllByGender(gender);
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void saveOrUpdateUser(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteUser(Integer username) {
		userRepository.deleteById(username);
	}

	@Override
	public int getTotalUsers() {
		return (int) userRepository.count(); // Assuming count() returns total user count
	}

	@Override
	public int getTotalProducts() {
		// Assuming there is a ProductRepository
		// return productRepository.countTotalProducts();
		return 0; // Placeholder, replace with actual implementation
	}

	// Phương thức cập nhật vai trò người dùng thành seller
	@Override
	public void updateUserRoleToSeller(int userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			Role currentRole = user.getRoleId(); // Lấy vai trò hiện tại của user

			if (currentRole != null && currentRole.getRoleId() == 3) {
				// Tìm đối tượng Role với role_id = 2 (Seller)
				Optional<Role> sellerRole = rolesRepository.findById(2);

				if (sellerRole.isPresent()) {
					user.setRoleId(sellerRole.get()); // Cập nhật vai trò thành Seller (role_id = 2)
					userRepository.save(user); // Lưu thay đổi
				}
			}
		}
	}

	@Override
	public void updateUser(UserDTO userDTO) {
		Optional<User> optionalUser = userRepository.findById(userDTO.getUsersId());
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setUsername(userDTO.getUsername());
			user.setFullName(userDTO.getFullName());
			user.setPassword(userDTO.getPassword()); // Có thể bỏ qua nếu không muốn cập nhật mật khẩu
			user.setProfileImage(userDTO.getProfileImage());
			user.setBirthDate(userDTO.getBirthDate());
			user.setGender(userDTO.getGender());
			user.setEmail(userDTO.getEmail());
			user.setPhone(userDTO.getPhone());

			// Cập nhật roleId (vai trò) nếu cần thiết
			Optional<Role> optionalRole = rolesRepository.findById(userDTO.getRoleId());
			if (optionalRole.isPresent()) {
				Role role = optionalRole.get();
				user.setRoleId(role); // Gán role mới cho user
			} else {
				throw new RuntimeException("Role not found with id: " + userDTO.getRoleId());
			}

			// Cập nhật statusId (trạng thái) nếu cần thiết
			Optional<UserStatus> optionalStatus = userStatusRepository.findById(userDTO.getStatusId());
			if (optionalStatus.isPresent()) {
				UserStatus status = optionalStatus.get();
				user.setStatus(status); // Gán status mới cho user
			} else {
				throw new RuntimeException("Status not found with id: " + userDTO.getStatusId());
			}

			// Lưu người dùng sau khi cập nhật
			userRepository.save(user);
		} else {
			throw new RuntimeException("User not found with id: " + userDTO.getUsersId());
		}
	}

	// tìm kiếm
	@Override
	public Page<User> searchUsers(String keyword, Pageable pageable) {
		return userRepository.searchUsers(keyword, pageable);
	}
}
