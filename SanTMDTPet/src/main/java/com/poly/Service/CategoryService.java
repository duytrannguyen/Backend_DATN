package com.poly.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Category;
import com.poly.Reponsitory.CategoryRepository;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findByCategoryCode(int categoryCode) {
        return categoryRepository.findById(categoryCode).orElse(null);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(int categoryCode) {
        categoryRepository.deleteById(categoryCode);
    }
    public List<Category> getAllCategory() {
		return categoryRepository.findAll();
	}
}
