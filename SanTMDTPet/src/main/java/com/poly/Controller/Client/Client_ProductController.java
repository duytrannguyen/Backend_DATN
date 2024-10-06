package com.poly.Controller.Client;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.Product;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Service.ProductService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/home")
public class Client_ProductController {
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ProductService productService;

	// Hiển thị tất cả sản phẩm
	@GetMapping("/products/all")
	public List<Product> getProductsAll() {
		return productRepository.findAll();
	}

	// API để lấy danh sách sản phẩm theo seller_id
	@GetMapping("/products/seller/{sellerId}")
	public List<Product> getProductsBySellerId(@PathVariable int sellerId) {
		return productRepository.findBySeller_SellerId(sellerId); // Gọi phương thức từ repository để lấy sản phẩm theo
																	// seller_id
	}

	// Hiển thị sản phẩm theo id
	@GetMapping("/products/{id}")
	public Product getProductById(@PathVariable int id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht id " + id));
	}

	// Hiển thị sẩn phẩm theo ngày đăng bán
	@GetMapping("/products/postingDate")
	public Map<String, List<Product>> getPostingDateProducts() {
		List<Product> products = productService.getAllProducts(); // Lấy tất cả sản phẩm

		List<Product> currentProducts = new ArrayList<>(); // Danh sách sản phẩm hiện tại
		List<Product> upcomingProducts = new ArrayList<>(); // Danh sách sản phẩm sắp bán
		Date currentDate = new Date(); // Lấy ngày hiện tại

		// Duyệt qua từng sản phẩm để phân loại
		for (Product product : products) {
			// Kiểm tra nếu ngày đăng nhỏ hơn hoặc bằng ngày hiện tại
			if (product.getPostingDate().before(currentDate) || product.getPostingDate().equals(currentDate)) {
				currentProducts.add(product); // Sản phẩm có sẵn
			} else {
				upcomingProducts.add(product); // Sản phẩm sắp bán
			}
		}

		// Trả về kết quả dưới dạng JSON, với cả hai danh sách sản phẩm hiện tại và sản
		// phẩm sắp bán
		Map<String, List<Product>> response = new HashMap<>();
		response.put("currentProducts", currentProducts);// sản phẩm hiện tại
		response.put("upcomingProducts", upcomingProducts);// sản phẩm sắp bán

		return response;
	}
	// Lọc và tìm kiếm sản phẩm

	@GetMapping("/products/page")
	public Map<String, Object> getMap(@RequestParam(name = "keyName", required = false) String keyName, // Tham số lọc
																										// theo tên sản
																										// phẩm, có thể
																										// bỏ trống
			@RequestParam(name = "categoryId", required = false) Integer categoryId, // Tham số lọc theo danh mục sản
																						// phẩm, có thể bỏ trống
			@RequestParam(name = "productId", required = false) Integer productId, // Tham số lọc theo ID sản phẩm, có
																					// thể bỏ trống
			@RequestParam(name = "page", defaultValue = "0") int page, // Số trang muốn lấy (mặc định là trang 0)
			@RequestParam(name = "size", defaultValue = "10") int size) { // Kích thước của trang (mặc định là 10 sản
																			// phẩm/trang)

		// Tạo đối tượng Pageable để thực hiện phân trang
		Pageable pageable = PageRequest.of(page, size);

		// Khai báo đối tượng Page để chứa dữ liệu sản phẩm sau khi tìm kiếm
		Page<Product> productPage;

		// Kiểm tra nếu có tham số keyName (tên sản phẩm) được truyền vào, thì lọc sản
		// phẩm theo tên
		if (StringUtils.hasText(keyName)) {
			productPage = productRepository.findByProductNameContaining(keyName, pageable);
		}
		// Nếu không có tham số keyName nhưng có categoryId, thì lọc sản phẩm theo danh
		// mục
		else if (categoryId != null) {
			productPage = productRepository.findProductsByCategory(categoryId, pageable);
		}
		// Nếu không có cả keyName và categoryId, thì trả về tất cả sản phẩm
		else {
			productPage = productRepository.findAll(pageable);
		}

		// Tạo đối tượng Map để trả về kết quả dưới dạng JSON, gồm cả danh sách sản phẩm
		// và thông tin phân trang
		Map<String, Object> response = new HashMap<>();

		// Thêm danh sách sản phẩm hiện tại vào map
		response.put("products", productPage.getContent()); // Lấy danh sách sản phẩm trong trang hiện tại
		response.put("currentPage", productPage.getNumber()); // Số của trang hiện tại (bắt đầu từ 0)
		response.put("totalItems", productPage.getTotalElements()); // Tổng số sản phẩm trong tất cả các trang
		response.put("totalPages", productPage.getTotalPages()); // Tổng số trang

		// Trả về map chứa dữ liệu sản phẩm và thông tin phân trang
		return response;
	}

}
