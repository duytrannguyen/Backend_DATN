package com.poly.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.poly.Model.Category;
import com.poly.dto.CategoryDTO;

@Service
public interface CategoryService {
	List<Category> findAllCategories();

//	Category findByCategoryId(Integer categoryId);

	Category saveCategory(Category category);

	void deleteCategory(Integer categoryId);

	// Tìm danh mục theo tên
	Category findByCategoryName(String categoryName);

	boolean existsByCategoryName(String categoryName);

	// Thêm phương thức để kiểm tra sự tồn tại của danh mục theo ID
	boolean existsByCategoryId(Integer categoryId);

	CategoryDTO findByCategoryId(Integer categoryId); // Cập nhật trả về DTO

}