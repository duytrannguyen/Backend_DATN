package com.poly.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Product;
import com.poly.Reponsitory.ProductRepository;
import com.poly.dto.ProductDTO;

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

	// lọc sản phẩm

	public List<Product> getProductsByCategoryId(int categoryId) {
		return productRepository.findByCategoryCategoryId(categoryId);
	}

	// report
	// Lấy tổng số sản phẩm của seller dựa trên sellerId
	public int getTotalProductsBySeller(int sellerId) {
		return productRepository.countBySeller_sellerId(sellerId);
	}

	public List<ProductDTO> getAllProductsBySeller(int sellerId) {
		// Lấy danh sách sản phẩm bán chạy nhất của seller
		List<Product> topProducts = productRepository.findTop3BySellerId(sellerId, null);

		// Chuyển đổi danh sách Product sang ProductDTO
		return topProducts.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	public List<ProductDTO> getTop3BestSellingProductsBySellerId(int sellerId) {
		// Lấy danh sách sản phẩm bán chạy nhất của seller
		List<Product> topProducts = productRepository.findTop3BySellerId(sellerId, null);

		// Chuyển đổi danh sách Product sang ProductDTO
		return topProducts.stream()
				.map(this::convertToDTO)
				.collect(Collectors.toList());
	}

	private ProductDTO convertToDTO(Product product) {
		ProductDTO productDTO = new ProductDTO();
		// Giả sử bạn có các trường trong ProductDTO tương ứng với Product
		productDTO.setProductId(product.getProductId());
		productDTO.setProductName(product.getProductName());
		productDTO.setPrice(product.getPrice());
		productDTO.setSize(product.getSize());
		productDTO.setMaterial(product.getMaterial());
		productDTO.setDescription(product.getDescription());
		productDTO.setPlaceProduction(product.getPlaceProduction());
		productDTO.setPostingDate(product.getPostingDate());
		productDTO.setQuantity(product.getQuantity());
		productDTO.setCategoryName(product.getCategory().getCategoryName());
		productDTO.setImageUrl(product.getImageId().getImageName());
		productDTO.setSellerName(product.getSeller().getShopName());
		// productDTO.setStatus(product.getStatus());
		return productDTO;
	}
}