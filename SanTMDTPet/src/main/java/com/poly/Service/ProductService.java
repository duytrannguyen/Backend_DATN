package com.poly.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Product;
import com.poly.Reponsitory.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Tìm sản phẩm theo productId
    @Transactional
    public Product findByProductId(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findByProductId(productId);
        return optionalProduct.orElse(null);
    }

    // Tìm sản phẩm theo id (dành cho khoá chính)
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    // Lưu sản phẩm
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    // Xóa sản phẩm
    public void deleteProduct(Product product) {
        productRepository.delete(product);
    }

    // Lấy danh sách tất cả các sản phẩm
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Đếm tổng số lượng sản phẩm
    public int getTotalProducts() {
        return (int) productRepository.count();
    }

    // Kiểm tra xem sản phẩm đã tồn tại hay chưa dựa trên tên
    public boolean existsByProductName(String productName) {
        return productRepository.existsByProductName(productName);
    }

    // Lọc sản phẩm theo categoryId
    public List<Product> getProductsByCategoryId(int categoryId) {
        return productRepository.findByCategoryCategoryId(categoryId);
    }

    // Xóa sản phẩm theo productId
    public void deleteProductById(Integer productId) {
        Optional<Product> product = productRepository.findByProductId(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new RuntimeException("Không tìm thấy sản phẩm với ID: " + productId);
        }
    }
}
