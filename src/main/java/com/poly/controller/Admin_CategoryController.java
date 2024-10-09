package com.poly.controller;

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

import com.poly.model.CategoriesStatus;
import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.repository.CategoriesStatuRsepository;
import com.poly.repository.CategoryRepository;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin")
public class Admin_CategoryController {

	CategoryRepository categoryRepository;
	@Autowired
	CategoriesStatuRsepository categoriesStatusRepository;

	@Autowired
	public Admin_CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/Category")
	public String showCategories(Model model, HttpServletRequest req) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		List<CategoriesStatus> categoriesStatus = categoriesStatusRepository.findAll(); // Đổi tên biến
																						// categoriesStatuRsepository
																						// thành
																						// categoriesStatusRepository
		model.addAttribute("categoriesStatus", categoriesStatus);
		req.setAttribute("view", "/admin/QuanLyTheLoai/index.html");
		return "indexAdmin";
	}

	@GetMapping("/Category/edit/{categoryId}")
	public String edit(Model model, HttpServletRequest req, @PathVariable("categoryId") Integer id) {
		// Tìm category theo id
		Category item = categoryRepository.findById(id).orElse(null);

		// Nếu không tìm thấy category, có thể trả về trang lỗi hoặc redirect
		if (item == null) {
			model.addAttribute("errorMessage", "Thể loại không tồn tại!");
			return "redirect:/admin/Category"; // Hoặc trả về view thông báo lỗi
		}

		// Nếu category.statusId bị null, khởi tạo đối tượng mới
		if (item.getStatusId() == null) {
			item.setStatusId(new CategoriesStatus());
		}

		// Thêm category vào model
		model.addAttribute("category", item);

		// Tìm tất cả các trạng thái để hiển thị trong dropdown
		List<CategoriesStatus> categoriesStatus = categoriesStatusRepository.findAll(); // Sửa lại để lấy tất cả trạng
																						// thái
		model.addAttribute("categoriesStatus", categoriesStatus);

		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Thiết lập view
		req.setAttribute("view", "/admin/QuanLyTheLoai/index.html");
		return "indexAdmin";
	}

	@PostMapping("/Category/create")
	public String addCategory(Model model, @ModelAttribute Category category, RedirectAttributes redirectAttributes) {
	    categoryRepository.save(category);
	    redirectAttributes.addFlashAttribute("successMessage", "Thêm thành công!"); // Thêm thông báo thành công
	    return "redirect:/admin/Category"; // Chuyển hướng đến danh sách thể loại
	}

	@RequestMapping("/Category/update/{categoryId}")
	public String update(Model model, HttpServletRequest req, @PathVariable("categoryId") Integer id,
	        @RequestParam("categoryName") String categoryName,
	        @RequestParam("statusId") int statusId, RedirectAttributes redirectAttributes) {
	    Optional<Category> optionalCategory = categoryRepository.findById(id);

	    if (optionalCategory.isPresent()) {
	        Category category = optionalCategory.get();
	        category.setCategoryName(categoryName);

	        Optional<CategoriesStatus> optionalStatus = categoriesStatusRepository.findById(statusId);
	        if (optionalStatus.isPresent()) {
	            category.setStatusId(optionalStatus.get());
	        } else {
	            redirectAttributes.addFlashAttribute("errorMessage", "Trạng thái không tồn tại!");
	            return "redirect:/admin/Category";
	        }

	        categoryRepository.save(category);
	        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật thể loại thành công!");
	    } else {
	        redirectAttributes.addFlashAttribute("errorMessage", "Thể loại không tồn tại!");
	    }

	    return "redirect:/admin/Category";
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
	        return "redirect:/admin/Category";
	    }



	@PostMapping("/Category/reset")
	public String restProducts(HttpServletRequest req, Model model) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);
		List<CategoriesStatus> categoriesStatus = categoriesStatusRepository.findAll();
		model.addAttribute("categoriesStatus", categoriesStatus);
		req.setAttribute("view", "/admin/QuanLyTheLoai/index.html");
		return "indexAdmin";
	}
}
