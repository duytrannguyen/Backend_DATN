package com.poly.Service.lmpl;

import static com.poly.constant.GoogleConstant.GCLI;
import static com.poly.constant.GoogleConstant.GCLS;
import static com.poly.constant.GoogleConstant.GOOGLE_GRANT_TYPE;
import static com.poly.constant.GoogleConstant.GOOGLE_LINK_GET_TOKEN;
import static com.poly.constant.GoogleConstant.GOOGLE_LINK_GET_USER_INFO;
import static com.poly.constant.GoogleConstant.GOOGLE_REDIRECT_URI;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.poly.Model.Address;
import com.poly.Model.Role;
import com.poly.Model.User;
import com.poly.Model.UserStatus;
import com.poly.Reponsitory.RolesRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Reponsitory.UserStatusRepository;
import com.poly.Service.EmailService;
import com.poly.Service.FileStorageService;
import com.poly.Service.SessionService;
import com.poly.Service.UserService;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.dto.UserDTO;
import com.poly.dto.request.UserRequest;
import com.poly.repo.AddressRepo;
import com.poly.repo.RoleRepo;
import com.poly.repo.UserRepo;
import com.poly.repo.UserStatusRepo;

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
	private RoleRepo roleRepo;

	@Autowired
	UserStatusRepository userStatusRepository;
	@Autowired
	FileStorageService fileStorageService;
	@Autowired
	UserRepo userRepo;
	@Autowired
	EmailService emailService;
	@Autowired
	UserStatusRepo userStatusRepo;
	@Autowired
	AddressRepo addressRepo;

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

	@Override
	public User getUserByHolder() {
		SecurityContext securityContext = SecurityContextHolder.getContext();

		if (securityContext != null) {
			UserDetails userDetails = (UserDetails) securityContext.getAuthentication().getPrincipal();
			return userRepository.findByUsername(userDetails.getUsername());
		}
		return null;
	}

	@Override
	public Resource loadImage(String fileName, HttpHeaders headers) {
		Resource resource = fileStorageService.load(fileName);
		if (resource != null) {
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"");
			headers.setContentType(MediaType.IMAGE_JPEG);
			return new InputStreamResource(resource);
		} else {
			throw new RuntimeException("File not found");
		}
	}

	private Integer id = 0;

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public String getTokenGoogle(String code) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(GOOGLE_LINK_GET_TOKEN))
				.POST(HttpRequest.BodyPublishers
						.ofString("client_id=" + GCLI + "&client_secret=" + GCLS + "&redirect_uri="
								+ GOOGLE_REDIRECT_URI + "&code=" + code + "&grant_type=" + GOOGLE_GRANT_TYPE))
				.header("Content-Type", "application/x-www-form-urlencoded").build();

		String responseBody = "";
		String accessToken = "";
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				responseBody = response.body();
			} else {
				System.out.println("Error: " + response.statusCode());
			}
			if (!(responseBody.isEmpty())) {
				JsonObject jobj = JsonParser.parseString(responseBody).getAsJsonObject();
				accessToken = jobj.get("access_token").toString().replaceAll("\"", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return accessToken;
	}

	@Override
	public User GoogleAccountGetUserInfo(String accessToken) {
		String link = GOOGLE_LINK_GET_USER_INFO + accessToken;
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(link)).GET().build();

		String responseBody = "";
		try {
			HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
			if (response.statusCode() == 200) {
				responseBody = response.body();
			} else {
				System.out.println("Error: " + response.statusCode());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// parse json
		JsonObject jobj = JsonParser.parseString(responseBody).getAsJsonObject();
		String email = jobj.get("email").toString().replaceAll("\"", "");
		String fullName = jobj.get("name").toString().replaceAll("\"", "");
		String gender = jobj.get("gender").toString().replaceAll("\"", "");
		String id = jobj.get("id").toString().replaceAll("\"", "");

		return new User(null, id, email, fullName, gender, null, null, id, id, null, null, null); // Thay đổi theo cấu
																									// trúc User
	}

//	@Override
//	public List<User> getUsersByStatus(Integer statusId) {
//		return userRepository.findByStatusId(statusId);
//	}

	@Override
	public User uploadFile(MultipartFile file, Integer id) {
		User user = userRepo.findByUsersId(id);
		if (user != null) {
			if (file != null) {
				UUID uuid = UUID.randomUUID();
				fileStorageService.save(file, uuid);// Save file to server
				String fileUUID = uuid + (file.getOriginalFilename() != null
						? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
						: "");
				user.setProfileImage(fileUUID);
				return userRepo.save(user);
			}
		}
		return null;
	}

	@Override
	public User login(UserRequest userRequest) {
		User user = findByEmail(userRequest.getEmail());
		if (user != null && encoder.matches(userRequest.getPassword(), user.getPassword())) {
			id = user.getUsersId();
			return user;
		} else {
			return null;
		}
	}

	@Override
	public User register(UserRequest userRequest) {

		User user = new User();

		if (!userRepo.existsByEmail(userRequest.getEmail())) {

			user.setFullName("");
			user.setUsername("user" + UUID.randomUUID().toString().substring(0, 5).replaceAll("-", ""));
			user.setEmail(userRequest.getEmail());
			user.setPassword(encoder.encode(userRequest.getPassword()));
			user.setPhone("");
			user.setGender(true);
			user.setBirthDate(convertStringToDate(LocalDate.now().toString()));
			user.setProfileImage("");
			user.setRoleId(roleRepo.findById(2).orElse(null));
			UserStatus userStatus = userStatusRepo.findById(1).orElse(null);
			user.setStatus(userStatus);

			User save = userRepo.save(user);
			if (save != null) {
				String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", user.getPassword(),
						false);
				emailService.send(user.getEmail(), buildEmail, "Register success");
			} else {
				throw new RuntimeException("Register failed");
			}

			return user;
		} else {
			return null;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	@Override
	public boolean forgotPassword(String email) {
		User user = userRepo.findByEmail(email);
		if (user != null) {
			UUID uuid = UUID.randomUUID();
			String newPassword = uuid.toString().substring(0, 6).replaceAll("-", "");
			user.setPassword(encoder.encode(newPassword));
			String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", newPassword, true);
			if (emailService.send(user.getEmail(), buildEmail, "Forgot password")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public User changePassword(Integer id, UserRequest userRequest) {
		User user = userRepo.findById(id).orElse(null);
		if (user != null && encoder.matches(userRequest.getCurrentPassword(), user.getPassword())) {
			user.setPassword(encoder.encode(userRequest.getConfirmNewPassword()));
			String buildEmail = emailService.buildEmail(user.getEmail(), user.getUsersId() + "", "", true);
			emailService.send(user.getEmail(), buildEmail, "You Just Change Password");
			return userRepo.save(user);
		} else {
			return null;
		}
	}

	@Override
	public User edit(Integer id, UserRequest userRequest, MultipartFile file) {

		User user = userRepo.findByUsersId(id);

		if (user != null) {
			user.setFullName(userRequest.getFullName());
			user.setUsername(userRequest.getUserName());
			user.setPhone(userRequest.getPhoneNumber());
			if (convertStringToDate(userRequest.getBirthDate()) != null) {
				user.setBirthDate(convertStringToDate(userRequest.getBirthDate()));
				user.setGender(userRequest.getGender());
				if (file != null) {
					UUID uuid = UUID.randomUUID();
					fileStorageService.save(file, uuid);// Save file to server
					String fileUUID = uuid + (file.getOriginalFilename() != null
							? file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'))
							: "");
					user.setProfileImage(fileUUID);
					User save = userRepo.save(user);
					return save;
				} else {
					User save = userRepo.save(user);
					return save;
				}
			}
		}
		return null;
	}

	@Override
	public List<Address> getAddress(Integer id) {
		List<Address> byUsersId = addressRepo.findByUsers_id(id);
		if (byUsersId != null) {
			return byUsersId;
		} else {
			return null;
		}

	}

	@Override
	public Address deleteAddress(Integer id, Integer addressId) {
		Address address = addressRepo.findByUsers_idAndId(id, addressId);
		if (address != null) {
			address.setStatus(true);
			return addressRepo.save(address);
		}
		return null;
	}

	public Date convertStringToDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Address addAddress(Integer id, UserRequest userRequest) {
		// TODO Auto-generated method stub
		return null;
	}
}
