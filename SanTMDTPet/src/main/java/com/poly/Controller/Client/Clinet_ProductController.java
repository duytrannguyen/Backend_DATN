package com.poly.Controller.Client;

import org.springframework.web.bind.annotation.RestController;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.Category;
import com.poly.Model.Product;
import com.poly.Reponsitory.CategoryRepository;
import com.poly.Reponsitory.ProductRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/home")
public class Clinet_ProductController{
	@Autowired
	ProductRepository productRepository;

	@Autowired
	CategoryRepository categoriesRepository;
	
    // API để lấy danh sách sản phẩm theo seller_id
    @GetMapping("/products/seller/{sellerId}")
    public List<Product> getProductsBySellerId(@PathVariable int sellerId) {
        return productRepository.findBySeller_SellerId(sellerId); // Gọi phương thức từ repository để lấy sản phẩm theo seller_id
    }
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht id " + id));
	}
//	 // Hiển thị tất cả danh mục
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoriesRepository.findAll();
    }

    // Hiển thị danh mục theo ID
    @GetMapping("/categories/{id}")
    public Category getCategoryById(@PathVariable int id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id " + id));
    }
}
