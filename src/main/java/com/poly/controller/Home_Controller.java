package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.model.Product;
import com.poly.model.User;
import com.poly.service.InvoiceService;
import com.poly.service.ProductService;
import com.poly.service.SessionService;
import com.poly.service.UserService;
import com.poly.service.lmpl.UserServiceImpl;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("home")
public class Home_Controller {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ProductService productService;

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userServiceImpl;

	@RequestMapping("/login")
	public String Login() {
		return "login/logintest";
	}
//	@GetMapping("/abc")
//	@ResponseBody
//	public String abc(HttpSession session) {
//		//session.invalidate();
//		return "abc";
//	}

//	@PostMapping("/login")
//	public String login(@ModelAttribute LoginDTO loginDTO, RedirectAttributes redirectAttributes,Model model) {
//	    boolean result = userServiceImpl.login(loginDTO);
//
//	    if (result) {
//	        User user = userServiceImpl.getUserByUsername(loginDTO.getUsername());
//
//	        if (user != null) {
//	            int statusId = user.getStatusId().getStatusId();
//	            switch (statusId) {
//	                case 1: // Hoạt động
//	                	model.addAttribute("successMessage", "Đăng nhập thành công! Chào mừng bạn, " + user.getFullName() + ".");
////	                    redirectAttributes.addFlashAttribute("successMessage", "Đăng nhập thành công! Chào mừng bạn, " + user.getFullName() + ".");
//	                    return user.getRoleId().getRoleId() == 1 ? "redirect:/admin/baocaothongke/report" : "redirect:/home/index";
//	                case 2: // Không hoạt động
//	                	model.addAttribute("errorMessage", "Tài khoản của bạn không hoạt động.");
//	                    break;
//	                case 3: // Chờ xử lý
//	                	model.addAttribute("errorMessage", "Tài khoản của bạn đang chờ xử lý.");
//	                    break;
//	                case 4: // Tạm đình chỉ
//	                	model.addAttribute("errorMessage", "Tài khoản của bạn đã bị tạm đình chỉ.");
//	                    break;
//	                case 5: // Cấm vĩnh viễn
//	                	model.addAttribute("errorMessage", "Tài khoản của bạn đã bị cấm vĩnh viễn.");
//	                    break;
//	                default:
//	                	model.addAttribute("errorMessage", "Trạng thái tài khoản không xác định.");
//	                    break;
//	            }
//	        } else {
//	        	model.addAttribute("errorMessage", "Người dùng không được tìm thấy.");
//	        }
//	    } else {
//	    	model.addAttribute("errorMessage", "Thông tin đăng nhập không hợp lệ.");
//	    }
//
//	    return "redirect:/home/login"; // Chuyển hướng đến trang đăng nhập
//	}


	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home/index";
	}

	@RequestMapping("/dangky")
	public String register() {
		return "login/registertest";
	}

	@PostMapping("/dangky")
	public String register(@ModelAttribute RegisterDTO registerDTO, Model model) {
		boolean result = userService.register(registerDTO);
		if (result) {
			model.addAttribute("mess", "Account created successfully");
		} else {
			model.addAttribute("mess", "Username already exists");
		}
		return "login/registertest";
	}

//	@RequestMapping("/login/quenmk")
//	public String forgotPassword() {
//		return "login/forgotPassword";
//	}
//
//	@RequestMapping("/login/quenmk/hoantat")
//	public String finishForgotPassword() {
//		return "login/finishForgotPassword";
//	}
//
//	@RequestMapping("/login/doimk")
//	public String changePassword() {
//		return "users/changePassword";
//	}

	@GetMapping("/report")
	public String listProducts(Model model) {
		int totalCustomers = userService.getTotalUsers();
		int totalProducts = productService.getTotalProducts();
//        Double totalAmount = invoiceService.getTotalAmount();
//        Long totalDeliveredOrders = invoiceService.getTotalDeliveredOrders();

		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);

		model.addAttribute("totalCustomers", totalCustomers);
		model.addAttribute("totalProducts", totalProducts);
//        model.addAttribute("totalAmount", totalAmount);
//        model.addAttribute("totalDeliveredOrders", totalDeliveredOrders);

		return "admin/BaoCaoThongKe/Report";
	}
}
