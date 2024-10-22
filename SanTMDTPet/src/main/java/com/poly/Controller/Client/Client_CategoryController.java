package com.poly.Controller.Client;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Mapper.ProductMapper;
import com.poly.Model.Category;
import com.poly.Model.Product;
import com.poly.Reponsitory.CategoryRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.dto.ProductDTO;

@RestController
// @CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/home")
public class Client_CategoryController {
	@Autowired
	CategoryRepository categoriesRepository;
	@Autowired
	ProductRepository productRepository;

	// Hiển thị tất cả danh mục
	@GetMapping("/categories/all")
	public List<Category> getAllCategories() {
		return categoriesRepository.findAll();
	}

	// Hiển thị danh mục theo ID
	@GetMapping("/categories/{id}")
	public Category getCategoryById(@PathVariable int id) {
		return categoriesRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
	}

	@GetMapping("/categories/{id}/products")
	public List<ProductDTO> getProductsByCategoryId(@PathVariable int id) {
		List<Product> products = productRepository.findByCategoryCategoryId(id);
		return products.stream()
				.map(ProductMapper::toDTO)
				.collect(Collectors.toList());
	}

}
