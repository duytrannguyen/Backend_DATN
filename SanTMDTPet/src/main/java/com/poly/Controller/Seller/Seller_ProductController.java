package com.poly.Controller.Seller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.poly.Mapper.ProductMapper;
import com.poly.Model.Category;
import com.poly.Model.Image;
import com.poly.Model.Product;
import com.poly.Model.Seller;
import com.poly.Model.User;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Service.CategoryService;
import com.poly.Service.ImageService;
import com.poly.Service.ProductService;
import com.poly.dto.ProductDTO;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/seller/products")
public class Seller_ProductController {

	@Autowired
	ProductRepository productsRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	CategoryService categoryService;
	@Autowired
	ImageService imageService;
	@Autowired
	ProductService productService;

	@GetMapping("/list")
	public ResponseEntity<Page<ProductDTO>> listProducts(@RequestParam(name = "pageNo", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size, HttpServletRequest req) {

		// Lấy thông tin người dùng từ session
//		String username = req.getUserPrincipal().getName();
		User user = userRepository.findByUsername("DiPTB");

		// Kiểm tra xem user có null hay không
		if (user == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // Hoặc trả về thông báo lỗi
		}

		// Lấy danh sách seller từ thông tin người dùng
		List<Seller> sellers = user.getSellers(); // Lấy danh sách Seller từ User

		// Kiểm tra xem seller có tồn tại không
		if (sellers == null || sellers.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Hoặc trả về thông báo lỗi
		}

		// Giả sử bạn chỉ muốn làm việc với seller đầu tiên trong danh sách
		Seller seller = sellers.get(0); // Lấy seller đầu tiên

		// Sắp xếp theo ID sản phẩm giảm dần
		Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

		// Tìm sản phẩm theo seller
		Page<Product> productPage = productsRepository.findBySeller(seller, pageable);

		// Chuyển đổi Product thành ProductDTO (nếu cần)
		Page<ProductDTO> productDTOPage = productPage.map(product -> modelMapper.map(product, ProductDTO.class));

		return ResponseEntity.ok(productDTOPage);
	}

	@PostMapping("/create")
	public ResponseEntity<String> createProduct(@ModelAttribute ProductDTO productDTO,
			@RequestParam("img") MultipartFile photo) {

		List<String> errors = new ArrayList<>();

		// Validate các trường bắt buộc
		if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
			errors.add("Tên sản phẩm là bắt buộc.");
		}
		if (productDTO.getPrice() == 0) {
			errors.add("Giá bán là bắt buộc.");
		}
		if (productDTO.getPostingDate() == null) {
			errors.add("Ngày đăng bán là bắt buộc.");
		}
		if (productDTO.getCategoryName() == null) {
			errors.add("Mã danh mục là bắt buộc.");
		}

		// Kiểm tra lỗi
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(String.join(", ", errors));
		}

		try {
			Category category = categoryService.findByCategoryName(productDTO.getCategoryName());

			// Lấy thông tin người dùng từ session hoặc security context
	        String username = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByUsername(username);

			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại.");
			}

			List<Seller> sellers = user.getSellers();

			if (sellers == null || sellers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có người bán.");
			}

			Seller seller = sellers.get(0); // Sử dụng seller đầu tiên

			Product product = new Product();
			product.setProductName(productDTO.getProductName());
			product.setPrice(productDTO.getPrice());
			product.setSize(productDTO.getSize());
			product.setMaterial(productDTO.getMaterial());
			product.setDescription(productDTO.getDescription());
			product.setPlaceProduction(productDTO.getPlaceProduction());
			product.setPostingDate(productDTO.getPostingDate());
			product.setQuantity(productDTO.getQuantity());
			product.setStatus(productDTO.getStatusName());
			product.setCategory(category);
			product.setSeller(seller);

			// Xử lý upload ảnh nếu có
			if (photo != null && !photo.isEmpty()) {
				String originalFileName = StringUtils.cleanPath(photo.getOriginalFilename());
				String uploadDir = "D:\\Frontend_DATN\\frontend\\public\\Image_SP";

				// Kiểm tra và tạo thư mục nếu chưa tồn tại
				Path path = Paths.get(uploadDir);
				if (Files.notExists(path)) {
					Files.createDirectories(path);
				}

				// Chuẩn hóa tên sản phẩm thành không dấu và thay thế khoảng trắng
				String normalizedProductName = Normalizer.normalize(product.getProductName(), Normalizer.Form.NFD)
						.replaceAll("[^\\p{ASCII}]", "") // Loại bỏ dấu tiếng Việt
						.replaceAll("\\s+", "_"); // Thay thế khoảng trắng bằng dấu gạch dưới

				// Lấy phần mở rộng của file
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				String newFileName = normalizedProductName + fileExtension;

				// Lưu file ảnh
				Path filePath = Paths.get(uploadDir, newFileName);
				Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Lưu thông tin ảnh vào cơ sở dữ liệu
				Image image = new Image();
				image.setImageName("images.jpg");
				imageService.saveImage(image);

				// Liên kết đối tượng Image với Product trước khi lưu
				product.setImageId(image);
			}

			productService.saveProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body("Tạo sản phẩm thành công!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Object> getProductById(@PathVariable("id") Integer productId) {
		try {
			Product product = productService.findByProductId(productId);

			// Chuyển đổi từ Product sang ProductDTO
			ProductDTO productDTO = ProductMapper.toDTO(product);

			// Trả về ProductDTO
			return ResponseEntity.ok(productDTO);
		} catch (RuntimeException e) {
			// Trả về thông báo lỗi với status 404 nếu sản phẩm không tồn tại
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm với ID: " + productId);
		}
	}

	@PutMapping("/update/{productId}")
	public ResponseEntity<String> updateProduct(@PathVariable Integer productId, @ModelAttribute ProductDTO productDTO,
			@RequestParam("img") MultipartFile photo) {

		List<String> errors = new ArrayList<>();

// Validate các trường bắt buộc
		if (productDTO.getProductName() == null || productDTO.getProductName().trim().isEmpty()) {
			errors.add("Tên sản phẩm là bắt buộc.");
		}
		if (productDTO.getPrice() == 0) {
			errors.add("Giá bán là bắt buộc.");
		}
		if (productDTO.getPostingDate() == null) {
			errors.add("Ngày đăng bán là bắt buộc.");
		}
		if (productDTO.getCategoryName() == null) {
			errors.add("Mã danh mục là bắt buộc.");
		}

// Kiểm tra lỗi
		if (!errors.isEmpty()) {
			return ResponseEntity.badRequest().body(String.join(", ", errors));
		}

		try {
// Tìm sản phẩm theo ID
			Product product = productService.findByProductId(productId);
			if (product == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tồn tại.");
			}

// Tìm category theo tên
			Category category = categoryService.findByCategoryName(productDTO.getCategoryName());

// Lấy thông tin người dùng từ session hoặc security context
	        String username = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userRepository.findByUsername(username);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Người dùng không tồn tại.");
			}

			List<Seller> sellers = user.getSellers();
			if (sellers == null || sellers.isEmpty()) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Không có người bán.");
			}

			Seller seller = sellers.get(0); // Sử dụng seller đầu tiên

// Cập nhật thông tin sản phẩm
			product.setProductName(productDTO.getProductName());
			product.setPrice(productDTO.getPrice());
			product.setSize(productDTO.getSize());
			product.setMaterial(productDTO.getMaterial());
			product.setDescription(productDTO.getDescription());
			product.setPlaceProduction(productDTO.getPlaceProduction());
			product.setPostingDate(productDTO.getPostingDate());
			product.setQuantity(productDTO.getQuantity());
			product.setStatus(productDTO.getStatusName());
			product.setCategory(category);
			product.setSeller(seller);

			// Xử lý upload ảnh nếu có
			if (photo != null && !photo.isEmpty()) {
				String originalFileName = StringUtils.cleanPath(photo.getOriginalFilename());
				String uploadDir = "D:\\Frontend_DATN\\frontend\\public\\Image_SP";

				Path path = Paths.get(uploadDir);
				if (Files.notExists(path)) {
					Files.createDirectories(path);
				}

				String normalizedProductName = Normalizer.normalize(product.getProductName(), Normalizer.Form.NFD)
						.replaceAll("[^\\p{ASCII}]", "").replaceAll("\\s+", "_");

				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
				String newFileName = normalizedProductName + fileExtension;

				Path filePath = Paths.get(uploadDir, newFileName);
				Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				Image image = new Image();
				image.setImageName(newFileName);
				imageService.saveImage(image);

				product.setImageId(image);
			}

			productService.saveProduct(product);
			return ResponseEntity.ok("Cập nhật sản phẩm thành công!");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable("id") Integer productId) {
		try {
			// Gọi phương thức service để xóa sản phẩm
			productService.deleteProductById(productId);
			return ResponseEntity.ok("Xóa sản phẩm thành công!");
		} catch (RuntimeException e) {
			// Trả về thông báo lỗi nếu không tìm thấy sản phẩm
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Không tìm thấy sản phẩm với ID: " + productId);
		} catch (Exception e) {
			// Trả về lỗi khác nếu có
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
		}
	}

}