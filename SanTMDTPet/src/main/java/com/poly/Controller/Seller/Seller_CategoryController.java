package com.poly.Controller.Seller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.Model.Category;
import com.poly.Reponsitory.CategoryRepository;
import com.poly.Service.CategoryService;
import com.poly.dto.CategoryDTO;

@Controller
@RequestMapping("api/seller/categories")
public class Seller_CategoryController {
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	CategoryService categoryService;

	@Autowired
	public Seller_CategoryController(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@GetMapping("/list")
	@ResponseBody
	public List<CategoryDTO> getCategories() {
		List<Category> categories = categoryRepository.findAll();
		return categories.stream().map(category -> {
			CategoryDTO dto = new CategoryDTO();
			dto.setCategoryId(category.getCategoryId());
			dto.setStatusName(category.getStatusName());
			dto.setCategoryName(category.getCategoryName());
			return dto;
		}).collect(Collectors.toList());
	}

	@PostMapping("/create")
	public ResponseEntity<String> addCategory(@RequestBody Category category) {
		try {
			// Kiểm tra xem tên danh mục đã tồn tại hay chưa
			if (categoryService.existsByCategoryName(category.getCategoryName())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Tên danh mục đã tồn tại.");
			}

			categoryService.saveCategory(category); // Lưu danh mục
			return ResponseEntity.status(HttpStatus.CREATED).body("Thêm danh mục thành công!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	 @GetMapping("/{id}")
	    public ResponseEntity<Object> getCategoryById(@PathVariable("id") Integer categoryId) {
	        CategoryDTO categoryDTO = categoryService.findByCategoryId(categoryId);
	        if (categoryDTO != null) {
	            return ResponseEntity.ok(categoryDTO);  // Trả về DTO nếu tìm thấy
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body("Không tìm thấy danh mục với ID: " + categoryId);  // Trả về 404 nếu không tìm thấy
	        }
	    }

	@PutMapping("/update/{id}")
	public ResponseEntity<String> updateCategory(@PathVariable("id") Integer categoryId,
			@RequestBody Category category) {
		try {
			// Kiểm tra xem danh mục có tồn tại hay không
			if (!categoryService.existsByCategoryId(categoryId)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại.");
			}

			// Cập nhật thông tin danh mục
			category.setCategoryId(categoryId); // Đảm bảo rằng ID đúng được thiết lập
			categoryService.saveCategory(category);
			return ResponseEntity.ok("Cập nhật danh mục thành công!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id") Integer categoryId) {
		try {
			// Kiểm tra xem danh mục có tồn tại hay không
			if (!categoryService.existsByCategoryId(categoryId)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Danh mục không tồn tại.");
			}

			categoryService.deleteCategory(categoryId); // Xóa danh mục
			return ResponseEntity.ok("Xóa danh mục thành công!");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

}