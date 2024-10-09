package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.model.ProductStatus;
import com.poly.repository.CategoryRepository;
import com.poly.repository.ImageRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ProductStatusRepository;
import com.poly.repository.UserRepository;
import com.poly.service.CategoryService;
import com.poly.service.ImageService;
import com.poly.service.InvoiceService;
import com.poly.service.ProductService;
import com.poly.service.ProductStatusService;
import com.poly.service.UserService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin/baocaothongke")
public class Admin_HomeController {

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	ProductRepository productsRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ImageService imageService;

	@Autowired
	ServletContext context;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ProductStatusRepository productStatusRepository;
	@Autowired
	private ProductStatusService productStatusService; // Thêm dòng này vào controller

//	@GetMapping("/index")
//	public String homeAdmin() {
//		return "redirect:/admin/report";
//	}

//	@GetMapping("/index")
//	public String listProducts(Model model, HttpServletRequest req) {
//		int totalCustomers = userService.getTotalUsers();
//		int totalProducts = userService.getTotalProducts();
//		Double totalAmount = invoiceService.getTotalAmount();
//		Long totalOrders = invoiceService.getTotalOrders();
//		List<Product> products = productService.getAllProducts();
//		model.addAttribute("products", products);
//
//		model.addAttribute("totalCustomers", totalCustomers);
//		model.addAttribute("totalProducts", totalProducts);
//		model.addAttribute("totalAmount", totalAmount);
//		model.addAttribute("totalOrders", totalOrders);
//		req.setAttribute("view", "/admin/QuanLySanPham/Products.html");
//		return "indexAdmin";
//
//	}
	@GetMapping("/report")
	public String listProducts(Model model,HttpServletRequest req) {
		int totalCustomers = userService.getTotalUsers();
		int totalProducts = productService.getTotalProducts();
		Long totalOrders = invoiceService.getTotalOrders(); // Fetch total orders
		Double totalAmount = invoiceService.getTotalAmount(); // Fetch total amount

		List<Product> products = productService.getAllProducts();
		model.addAttribute("products", products);

		model.addAttribute("totalCustomers", totalCustomers);
		model.addAttribute("totalProducts", totalProducts);
		model.addAttribute("totalOrders", totalOrders); // Add total orders to the model
		model.addAttribute("totalAmount", totalAmount); // Add total amount to the model
		req.setAttribute("view", "/admin/BaoCaoThongKe/Report.html");
		return "indexAdmin";
	}

}