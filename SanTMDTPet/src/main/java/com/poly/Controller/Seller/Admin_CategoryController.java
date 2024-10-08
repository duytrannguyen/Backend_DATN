package com.poly.Controller.Seller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.Model.Category;
import com.poly.Reponsitory.CategoryRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("seller")
public class Admin_CategoryController {

	CategoryRepository categoryRepository;

	@Autowired
	public Admin_CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/Category")
	public String showCategories(Model model, HttpServletRequest req) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		req.setAttribute("view", "/Seller/QuanLyTheLoai/index.html");
		return "indexSeller";
	}

	@GetMapping("/Category/edit/{categoryId}")
	public String edit(Model model, HttpServletRequest req, @PathVariable("categoryId") Integer id) {
		// Tìm category theo id
		Category item = categoryRepository.findById(id).orElse(null);

		// Nếu không tìm thấy category, có thể trả về trang lỗi hoặc redirect
		if (item == null) {
			model.addAttribute("errorMessage", "Thể loại không tồn tại!");
			return "redirect:/seller/Category"; // Hoặc trả về view thông báo lỗi
		}

		// Thêm category vào model
		model.addAttribute("category", item);
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Thiết lập view
		req.setAttribute("view", "/Seller/QuanLyTheLoai/index.html");
		return "indexSeller";
	}

	@PostMapping("/Category/create")
	public String addCategory(Model model, @ModelAttribute Category category, RedirectAttributes redirectAttributes) {
		categoryRepository.save(category);
		redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công!");
		// Thêm thông báo thành công
		return "redirect:/seller/Category"; // Chuyển hướng đến danh sách thể loại
	}

	@RequestMapping("/Category/update/{categoryId}")
	public String update(Model model, HttpServletRequest req, @PathVariable("categoryId") Integer id,
			@RequestParam("categoryName") String categoryName, @RequestParam("ctatusName") String StatusName,
			RedirectAttributes redirectAttributes) {
		Optional<Category> optionalCategory = categoryRepository.findById(id);

		if (optionalCategory.isPresent()) {
			Category category = optionalCategory.get();
			category.setCategoryName(categoryName);
			category.setStatusName(StatusName);
			categoryRepository.save(category);
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thể loại thành công!");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Thể loại không tồn tại!");
		}

		return "redirect:/seller/Category";
	}

	@GetMapping("/Category/delete/{categoryId}")
	public String deleteCategory(@PathVariable("categoryId") Integer id, RedirectAttributes redirectAttributes) {
		try {
			if (categoryRepository.existsById(id)) {
				categoryRepository.deleteById(id);
				redirectAttributes.addFlashAttribute("successMessage", "Thể loại đã được xóa thành công!");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Thể loại không tồn tại!");
			}
		} catch (DataIntegrityViolationException e) {
			redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa thể loại này vì nó đang được sử dụng!");
		}
		return "redirect:/seller/Category";
	}

	@PostMapping("/Category/reset")
	public String restProducts(HttpServletRequest req, Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		req.setAttribute("view", "/Seller/QuanLyTheLoai/index.html");
		return "indexSeller";
	}
}