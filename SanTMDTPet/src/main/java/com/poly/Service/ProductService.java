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

	// Kiểm tra xem sản phẩm đã tồn tại hay chưa
	// public boolean existsByProductName(String productName) {
	// return productRepository.existsByProductName(productName);
	// }

	// lọc sản phẩm

	// public List<Product> getProductsByCategoryId(int categoryId) {
	// return productRepository.findByCategoryCategoryId(categoryId);
	// }

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
		productDTO.setCategoryId(product.getCategory().getCategoryId());
		productDTO.setSellerName(product.getSeller().getShopName());
		// productDTO.setStatus(product.getStatus());
		return productDTO;
	}

	// }

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
