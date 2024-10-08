package com.poly.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Product;
import com.poly.Reponsitory.ProductRepository;

import jakarta.transaction.Transactional;

@Service("ProductService")
public class ProductService {
	@Autowired
	private ProductRepository productRepository;

	@Transactional
	public Product findByProductId(Integer productId) {
		Optional<Product> optionalProduct = productRepository.findByProductId(productId);
		return optionalProduct.orElse(null);
	}

	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	public void deleteProduct(Product product) {
		productRepository.delete(product);
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public int getTotalProducts() {
		return (int) productRepository.count();
	}

	// Kiểm tra xem sản phẩm đã tồn tại hay chưa
    public boolean existsByProductName(String productName) {
        return productRepository.existsByProductName(productName);
    }

//    lọc sản phẩm

	public List<Product> getProductsByCategoryId(int categoryId) {
		return productRepository.findByCategoryCategoryId(categoryId);
	}

}