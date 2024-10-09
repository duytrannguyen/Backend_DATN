package com.poly.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.model.Category;
import com.poly.model.Product;
import com.poly.repository.CategoryRepository;

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
