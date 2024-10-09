package com.poly.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.model.Role;
import com.poly.model.User;
import com.poly.repository.RolesRepository;
import com.poly.repository.UserRepository;
import com.poly.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("user/client")
public class User_ClientController {

	// còn đang fix
	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
//	@Autowired
//	RolesRepository roleRepository;
	@Autowired
	ServletContext context;

	@GetMapping("/list")
	public String filterUsers(HttpServletRequest req, @RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(name = "pageNo", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, Model model) {

		// Sắp xếp theo ID giảm dần
		Pageable pageable = PageRequest.of(page, size, Sort.by("usersId").descending());

		// Lấy trang dữ liệu người dùng từ userRepository dựa trên giới tính và roleId
		Page<User> usersPage;
		if (keyword != null && !keyword.isEmpty()) {
			if (gender == null || gender.isEmpty()) {
				usersPage = userRepository.searchUsersWithRoleId2(keyword, pageable);
			} else {
				Boolean genderBoolean = Boolean.valueOf(gender);
				usersPage = userRepository.searchByGenderAndRoleId(keyword, genderBoolean, pageable);
			}
		} else {
			if (gender == null || gender.isEmpty()) {
				usersPage = userRepository.findAllUsersWithRoleId2(pageable);
			} else {
				Boolean genderBoolean = Boolean.valueOf(gender);
				usersPage = userRepository.findByGenderAndRoleId(genderBoolean, pageable);
			}
		}

		// Tổng số trang
		int totalPages = usersPage.getTotalPages();

		// Thêm các thông tin vào model
//	    model.addAttribute("Users", users);
//	    model.addAttribute("roles", roles);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", totalPages);
		model.addAttribute("selectedGender", gender);
		model.addAttribute("keyword", keyword);

		// Định tuyến tới view JSP
		req.setAttribute("view", "/views/admin/QuanLyKhachHang/Client.jsp");
		return "indexClient";
	}

	@GetMapping("/edit/{usersId}")
	public String edit(HttpServletRequest req, Model model, @PathVariable(name = "usersId") Integer id) {
		User item = userRepository.findById(id).orElse(null);
//	List<Role> roles = roleRepository.findAll();

		model.addAttribute("itemProd", item);
//	model.addAttribute("roles", roles);

		req.setAttribute("view", "/views/account/ThayDoiTTKhachHang/edit.jsp");
		return "indexClient";
	}

//	@PostMapping("/update/{usersId}")
//	public String update(HttpServletRequest req, @PathVariable("usersId") Integer usersId,
//			@RequestParam("username") String username, @RequestParam("fullName") String fullName,
//			@RequestParam("phone") String phone, @RequestParam("birthDate") String birthDateStr,
//			@RequestParam("gender") Boolean gender, @RequestParam("email") String email,
//			@RequestParam("address") String address,
//			@RequestParam(value = "profileImage", required = false) MultipartFile profileImage,
////                     @RequestParam("roleId") int roleId,
//			Model model) {
//
//		User user = userRepository.findById(usersId).orElseThrow(() -> new RuntimeException("User not found"));
//		Date birthDate = null;
//		try {
//			birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthDateStr);
//		} catch (ParseException e) {
//			e.printStackTrace();
//			req.setAttribute("error", "Invalid date format");
//			return "errorPage";
//		}
//		user.setUsername(username); // Ensure the username is also set
//		user.setFullName(fullName);
//		user.setPhone(phone);
//		user.setBirthDate(birthDate);
//		user.setGender(gender);
//		user.setEmail(email);
////		user.setAddress(address);
//
//		// Handle profile image upload if provided
//		if (profileImage != null && !profileImage.isEmpty()) {
//			try {
//				// Check file type
//				if (!profileImage.getContentType().startsWith("image")) {
//					req.setAttribute("error", "Only image files are allowed");
//					return "errorPage";
//				}
//
//				// Define upload directory
//				String uploadDir = "/Image_Users/";
//				String realPath = req.getServletContext().getRealPath(uploadDir);
//				Path path = Paths.get(realPath);
//				if (Files.notExists(path)) {
//					Files.createDirectories(path);
//				}
//
//				// Save image file to upload directory
//				String fileName = StringUtils.cleanPath(profileImage.getOriginalFilename());
//				Path filePath = Paths.get(realPath, fileName);
//				Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//				// Set the filename in the user object
//				user.setProfileImage(fileName);
//			} catch (IOException e) {
//				e.printStackTrace();
//				req.setAttribute("error", "Failed to upload image");
//				return "errorPage";
//			}
//		}
//		return address;
//	}

//    // Handle role update
//    Role role = roleRepository.findById(roleId)
//            .orElseThrow(() -> new RuntimeException("Role not found"));
//    user.setRoleId(role);
//
//    // Save updated user
//    userRepository.save(user);
//
//    // Add success message
//    model.addAttribute("successMessage", "Cập nhật người dùng thành công!");
//
//    return "redirect:/admin/client/list";
//}

	@GetMapping("/delete/{userId}")
	public String deleteUser(@PathVariable(name = "userId") Integer userId, Model model,
			RedirectAttributes redirectAttributes) {
		// Fetch the user by id
		try {
			User user = userRepository.findById(userId).orElse(null);

			if (user != null) {
				// Perform any additional operations if needed before deleting

				// Delete the user
				userRepository.delete(user);
				redirectAttributes.addFlashAttribute("toastMessage", "Xóa người dùng thành công!.");
			} else {
				// Add failure toast message
				redirectAttributes.addFlashAttribute("toastMessage", "Xóa người dùng thất bại!.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("toastMessage", "Đã xảy ra lỗi trong quá trình xóa người dùng!.");

		}

		return "redirect:/admin/client/list"; // Redirect to the list page after deletion
	}
}
