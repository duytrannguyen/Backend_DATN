package com.poly.Service.lmpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Category;
import com.poly.Reponsitory.CategoryRepository;
import com.poly.Service.CategoryService;
import com.poly.dto.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> findAllCategories() {
		return categoryRepository.findAll();
	}
	@Autowired
    private ModelMapper modelMapper; // Sử dụng ModelMapper để chuyển đổi

    @Override
    public CategoryDTO findByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElse(null);
        return category != null ? modelMapper.map(category, CategoryDTO.class) : null; // Chuyển đổi sang DTO
    }
//	@Override
//	public Category findByCategoryId(Integer categoryId) {
//		return categoryRepository.findById(categoryId)
//				.orElseThrow(() -> new RuntimeException("Không tìm thấy danh mục với ID: " + categoryId));
//	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		if (categoryRepository.existsById(categoryId)) {
			categoryRepository.deleteById(categoryId);
		} else {
			throw new RuntimeException("Không thể xóa, không tìm thấy danh mục với ID: " + categoryId);
		}
	}

	// Tìm danh mục theo tên
	@Override
	public Category findByCategoryName(String categoryName) {
		return categoryRepository.findByCategoryName(categoryName);
	}

	@Override
	public boolean existsByCategoryName(String categoryName) {
		return categoryRepository.existsByCategoryName(categoryName);
	}

	@Override
	public boolean existsByCategoryId(Integer categoryId) {
		return categoryRepository.existsById(categoryId);
	}
}