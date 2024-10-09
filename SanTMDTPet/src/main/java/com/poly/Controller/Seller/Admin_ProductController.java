package com.poly.Controller.Seller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.poly.Model.Category;
import com.poly.Model.Image;
import com.poly.Model.Product;
import com.poly.Model.ProductStatus;
import com.poly.Model.Seller;
import com.poly.Model.User;
import com.poly.Reponsitory.CategoryRepository;
import com.poly.Reponsitory.ImageRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Reponsitory.ProductStatusRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Service.CategoryService;
import com.poly.Service.ImageService;
import com.poly.Service.ProductService;
import com.poly.Service.ProductStatusService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("seller/products")
public class Admin_ProductController {
	@Autowired
	ProductRepository productsRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ImageService imageService;

	@Autowired
	ServletContext context;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ImageRepository imageRepository;
	@Autowired
	ProductStatusRepository productStatusRepository;
	@Autowired
	private ProductStatusService productStatusService; // Thêm dòng này vào controller

	@GetMapping("/list")
	public String listProducts(Model model, HttpServletRequest req,
			@RequestParam(name = "pageNo", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "10") int size) {

		// Lấy thông tin người dùng từ session
		String username = req.getUserPrincipal().getName();
		User user = userRepository.findByUsername(username);

		// Kiểm tra xem user có null hay không
		if (user == null) {
			return "redirect:/login"; // Hoặc trang lỗi
		}

		// Lấy danh sách seller từ thông tin người dùng
		List<Seller> sellers = user.getSellers(); // Lấy danh sách Seller từ User

		// Kiểm tra xem seller có tồn tại không
		if (sellers == null || sellers.isEmpty()) {
			return "redirect:/error seller"; // Hoặc trang lỗi
		}
		// Giả sử bạn chỉ muốn làm việc với seller đầu tiên trong danh sách
		Seller seller = sellers.get(0); // Lấy seller đầu tiên

		// Sắp xếp theo ID sản phẩm giảm dần
		Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

		// Tìm sản phẩm theo seller
		Page<Product> productPage = productsRepository.findBySeller(seller, pageable);

		// Thêm thông tin sản phẩm vào model
		model.addAttribute("pageProd", productPage.getContent());
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", productPage.getTotalPages());

		// Lấy danh sách thể loại
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Lấy danh sách trạng thái sản phẩm
		List<ProductStatus> productStatus = productStatusRepository.findAll();
		model.addAttribute("productStatus", productStatus);

		// Thiết lập view
		req.setAttribute("view", "/Seller/QuanLySanPham/Products.html");
		return "indexSeller";
	}

	@RequestMapping("/form")
	public String formProducts(Model model, HttpServletRequest req) {
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Giả sử bạn cần khởi tạo một sản phẩm mới
		Product itemProd = new Product(); // Khởi tạo đối tượng sản phẩm mới
		model.addAttribute("itemProd", itemProd); // Thêm vào model để truy cập trong template

		// Lấy danh sách trạng thái sản phẩm
		List<ProductStatus> productStatus = productStatusRepository.findAll();
		model.addAttribute("productStatus", productStatus);

		req.setAttribute("view", "/Seller/QuanLySanPham/add.html");
		return "indexSeller";
	}

	@PostMapping("/create")
	public String createProduct(Model model, HttpServletRequest req, @RequestParam("img") MultipartFile photo,
			@RequestParam("productName") String productName, @RequestParam("price") String priceStr,
			@RequestParam(value = "percentDecrease", required = false, defaultValue = "0") String percentDecreaseStr,
			@RequestParam("yearManufacture") String yearManufactureStr, @RequestParam("size") String size,
			@RequestParam("material") String material, @RequestParam("description") String description,
			@RequestParam("placeProduction") String placeProduction, @RequestParam("postingDate") String postingDateStr,
			@RequestParam(value = "quantity", required = false, defaultValue = "0") String quantityStr,
			@RequestParam("categoryId") String categoryIdStr, @RequestParam("statusId") String statusIdStr,
			RedirectAttributes redirectAttributes) {

		List<String> errors = new ArrayList<>();

		// Validate các trường bắt buộc
		if (productName == null || productName.trim().isEmpty()) {
			errors.add("Tên sản phẩm là bắt buộc.");
		}
		if (priceStr == null || priceStr.trim().isEmpty()) {
			errors.add("Giá bán là bắt buộc.");
		}
		if (yearManufactureStr == null || yearManufactureStr.trim().isEmpty()) {
			errors.add("Năm sản xuất là bắt buộc.");
		}
		if (size == null || size.trim().isEmpty()) {
			errors.add("Kích thước là bắt buộc.");
		}
		if (material == null || material.trim().isEmpty()) {
			errors.add("Chất liệu là bắt buộc.");
		}
		if (description == null || description.trim().isEmpty()) {
			errors.add("Mô tả là bắt buộc.");
		}
		if (placeProduction == null || placeProduction.trim().isEmpty()) {
			errors.add("Nơi sản xuất là bắt buộc.");
		}
		if (postingDateStr == null || postingDateStr.trim().isEmpty()) {
			errors.add("Ngày đăng bán là bắt buộc.");
		}
		if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
			errors.add("Mã danh mục là bắt buộc.");
		}
		if (statusIdStr == null || statusIdStr.trim().isEmpty()) {
			errors.add("Mã trạng thái là bắt buộc.");
		}

		// Validate các giá trị nhập vào
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
			if (price <= 0) {
				errors.add("Giá phải lớn hơn 0.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng giá không hợp lệ.");
		}

		float percentDecrease = 0;
		try {
			percentDecrease = Float.parseFloat(percentDecreaseStr);
			if (percentDecrease < 0) {
				errors.add("Phần trăm giảm giá không được âm.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng phần trăm giảm giá không hợp lệ.");
		}

		int yearManufacture = 0;
		try {
			yearManufacture = Integer.parseInt(yearManufactureStr);
			if (yearManufacture <= 0) {
				errors.add("Năm sản xuất phải là số dương.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng năm sản xuất không hợp lệ.");
		}

		int categoryId = 0;
		try {
			categoryId = Integer.parseInt(categoryIdStr);
		} catch (NumberFormatException e) {
			errors.add("Định dạng mã danh mục không hợp lệ.");
		}

		int statusId = 0;
		try {
			statusId = Integer.parseInt(statusIdStr);
		} catch (NumberFormatException e) {
			errors.add("Định dạng mã trạng thái không hợp lệ.");
		}

		Date postingDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			postingDate = dateFormat.parse(postingDateStr);
		} catch (ParseException e) {
			errors.add("Định dạng ngày đăng bán không hợp lệ.");
		}

		int quantity = 0;
		try {
			quantity = Integer.parseInt(quantityStr);
			if (quantity < 0) {
				errors.add("Số lượng không được âm.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng số lượng không hợp lệ.");
		}

		// Kiểm tra xem tên sản phẩm đã tồn tại chưa
//	    if (productService.existsByProductName(productName)) {
//	        errors.add("Sản phẩm với tên '" + productName + "' đã tồn tại trong hệ thống.");
//	    }

		// Kiểm tra lỗi
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", String.join(", ", errors));
			return "redirect:/seller/products/create";
		}

		try {
			Category category = categoryService.findByCategoryCode(categoryId);
			ProductStatus status = productStatusService.findByStatusId(statusId);

			// Lấy thông tin người dùng từ session
			String username = req.getUserPrincipal().getName();
			User user = userRepository.findByUsername(username);

			if (user == null) {
				return "redirect:/login"; // Người dùng không tồn tại
			}

			List<Seller> sellers = user.getSellers();

			if (sellers == null || sellers.isEmpty()) {
				return "redirect:/error"; // Không có người bán
			}

			Seller seller = sellers.get(0); // Sử dụng seller đầu tiên

			Product product = new Product();
			product.setProductName(productName);
			product.setPrice(price);
			product.setPercentDecrease(percentDecrease);
			product.setYearManufacture(yearManufacture);
			product.setSize(size);
			product.setMaterial(material);
			product.setDescription(description);
			product.setPlaceProduction(placeProduction);
			product.setPostingDate(postingDate);
			product.setQuantity(quantity);
			product.setCategory(category);
			product.setStatus(status);
			product.setSeller(seller);

//	        // Handle image upload if provided
//	        if (photo != null && !photo.isEmpty()) {
//	            String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
//	            
//	            // Thay đổi uploadDir thành đường dẫn mong muốn
//	            String uploadDir = "D:\\Frontend_DATN\\frontend\\public\\Image_SP";
//	            
//	            // Kiểm tra và tạo thư mục nếu chưa tồn tại
//	            Path path = Paths.get(uploadDir);
//	            if (Files.notExists(path)) {
//	                Files.createDirectories(path);
//	            }
//
//	            // Lưu file ảnh
//	            Path filePath = Paths.get(uploadDir, fileName);
//	            Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//
//	            // Save image details to database
//	            Image image = new Image();
//	            image.setImageName(fileName);
//	            imageService.saveImage(image);
//
//	            // Link Image object to Product before saving Product
//	            product.setImageId(image);
//	        }
			// Handle image upload if provided
			if (photo != null && !photo.isEmpty()) {
				String originalFileName = StringUtils.cleanPath(photo.getOriginalFilename());

				// Thay đổi uploadDir thành đường dẫn mong muốn
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

				// Tạo tên file mới bằng tên sản phẩm không dấu
				String newFileName = normalizedProductName + fileExtension;

				// Lưu file ảnh
				Path filePath = Paths.get(uploadDir, newFileName);
				Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Save image details to database
				Image image = new Image();
				image.setImageName(newFileName); // Lưu tên file mới vào đối tượng Image
				imageService.saveImage(image);

				// Link Image object to Product before saving Product
				product.setImageId(image);
			}

			productService.saveProduct(product);
			redirectAttributes.addFlashAttribute("successMessage", "Tạo sản phẩm thành công!");

		} catch (Exception e) {
			errors.add("Đã xảy ra lỗi: " + e.getMessage());
			redirectAttributes.addFlashAttribute("errorMessage", String.join(", ", errors));
			return "redirect:/seller/products/create";
		}

		return "redirect:/seller/products/list";
	}

	@GetMapping("/edit/{productId}")
	public String edit(HttpServletRequest req, Model model, @PathVariable(name = "productId") Integer id) {
		// Tìm sản phẩm theo id
		Product item = productsRepository.findById(id).orElse(null);
		if (item == null) {
			// Xử lý trường hợp sản phẩm không được tìm thấy
			return "redirect:/seller/products/list?error=ProductNotFound";
		}

		// Thêm sản phẩm vào mô hình
		model.addAttribute("itemProd", item);

		// Lấy danh sách danh mục
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Lấy danh sách trạng thái
		List<ProductStatus> productStatuses = productStatusRepository.findAll();
		model.addAttribute("productStatuses", productStatuses);

		// Thêm trạng thái hiện tại của sản phẩm vào mô hình
		model.addAttribute("currentStatusId", item.getStatus().getStatusId());

		// Thiết lập view
		req.setAttribute("view", "/Seller/QuanLySanPham/edit.html");
		return "indexSeller";
	}

	@PostMapping("/update/{productId}")
	public String updateProduct(Model model, @PathVariable(name = "productId") Integer productId,
			@RequestParam("productName") String productName, @RequestParam("price") String priceStr,
			@RequestParam("percentDecrease") String percentDecreaseStr,
			@RequestParam("yearManufacture") String yearManufactureStr, @RequestParam("size") String size,
			@RequestParam("material") String material, @RequestParam("description") String description,
			@RequestParam("postingDate") String postingDateStr,
			@RequestParam(value = "quantity", required = false, defaultValue = "0") String quantityStr,
			@RequestParam("categoryId") String categoryIdStr, @RequestParam("statusId") String statusIdStr,
			@RequestPart(value = "img", required = false) MultipartFile photo, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {

		List<String> errors = new ArrayList<>();

		// Validate productName
		if (productName == null || productName.trim().isEmpty()) {
			errors.add("Tên sản phẩm là bắt buộc.");
		}

		// Validate price
		float price = 0;
		try {
			price = Float.parseFloat(priceStr);
			if (price <= 0) {
				errors.add("Giá phải lớn hơn 0.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng giá không hợp lệ.");
		}

		// Validate percentDecrease
		float percentDecrease = 0;
		try {
			percentDecrease = Float.parseFloat(percentDecreaseStr);
			if (percentDecrease < 0) {
				errors.add("Phần trăm giảm giá không được âm.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng phần trăm giảm giá không hợp lệ.");
		}

		// Validate yearManufacture
		int yearManufacture = 0;
		try {
			yearManufacture = Integer.parseInt(yearManufactureStr);
			if (yearManufacture <= 0) {
				errors.add("Năm sản xuất phải là số nguyên dương.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng năm sản xuất không hợp lệ.");
		}

		// Validate quantity
		int quantity = 0;
		try {
			quantity = Integer.parseInt(quantityStr);
			if (quantity < 0) {
				errors.add("Số lượng không được âm.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng số lượng không hợp lệ.");
		}

		// Validate categoryId
		int categoryId = 0;
		try {
			categoryId = Integer.parseInt(categoryIdStr);
		} catch (NumberFormatException e) {
			errors.add("Định dạng mã danh mục không hợp lệ.");
		}

		// Validate statusId
		int statusId = 0;
		try {
			statusId = Integer.parseInt(statusIdStr);
		} catch (NumberFormatException e) {
			errors.add("Định dạng mã trạng thái không hợp lệ.");
		}

		// Validate postingDate
		Date postingDate = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			postingDate = dateFormat.parse(postingDateStr);
		} catch (ParseException e) {
			errors.add("Định dạng ngày đăng bán không hợp lệ.");
		}

		// Check for errors
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessages", errors);
			return "redirect:/seller/products/update/" + productId;
		}

		try {
			// Find Product by productId
			Product product = productService.findByProductId(productId);
			if (product == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại.");
				return "redirect:/seller/products/list";
			}

			// Find Category and Status by their IDs
			Category category = categoryService.findByCategoryCode(categoryId);
			ProductStatus status = productStatusService.findByStatusId(statusId);

			// Update product attributes
			product.setProductName(productName);
			product.setPrice(price);
			product.setPercentDecrease(percentDecrease);
			product.setYearManufacture(yearManufacture);
			product.setSize(size);
			product.setMaterial(material);
			product.setDescription(description);
			product.setPostingDate(postingDate);
			product.setQuantity(quantity);
			product.setCategory(category);
			product.setStatus(status);
			// Handle image upload if provided
			if (photo != null && !photo.isEmpty()) {
				String originalFileName = StringUtils.cleanPath(photo.getOriginalFilename());

				// Thay đổi uploadDir thành đường dẫn mong muốn
				String uploadDir = "D:\\Frontend_DATN\\frontend\\public\\Image_SP";

				// Kiểm tra và tạo thư mục nếu chưa tồn tại
				Path path = Paths.get(uploadDir);
				if (Files.notExists(path)) {
					Files.createDirectories(path);
				}

				// Lấy ảnh cũ nếu có
				Image oldImage = product.getImageId();
				if (oldImage != null) {
					// Đường dẫn ảnh cũ
					Path oldImagePath = Paths.get(uploadDir, oldImage.getImageName());

					// Xóa ảnh cũ nếu tồn tại
					try {
						Files.deleteIfExists(oldImagePath);
					} catch (IOException e) {
						System.err.println("Không thể xóa ảnh cũ: " + e.getMessage());
					}
				}

				// Chuẩn hóa tên sản phẩm thành không dấu và thay thế khoảng trắng
				String normalizedProductName = Normalizer.normalize(product.getProductName(), Normalizer.Form.NFD)
						.replaceAll("[^\\p{ASCII}]", "") // Loại bỏ dấu tiếng Việt
						.replaceAll("\\s+", "_"); // Thay thế khoảng trắng bằng dấu gạch dưới

				// Lấy phần mở rộng của file
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));

				// Tạo tên file mới bằng tên sản phẩm không dấu
				String newFileName = normalizedProductName + fileExtension;

				// Lưu file ảnh mới
				Path filePath = Paths.get(uploadDir, newFileName);
				Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Save new image details to database
				Image newImage = new Image();
				newImage.setImageName(newFileName); // Lưu tên file mới vào đối tượng Image
				imageService.saveImage(newImage);

				// Link new Image object to Product before saving Product
				product.setImageId(newImage);
			}

			// Save updated product to database
			productService.saveProduct(product);

			// Add success message
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");

		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
			return "redirect:/seller/products/update/" + productId;
		}

		// Redirect to the list of products
		return "redirect:/seller/products/list";
	}

	@GetMapping("/delete/{productId}")
	public String delete(@PathVariable(name = "productId") Integer id, Model model,
			RedirectAttributes redirectAttributes) {
		// Fetch the product by id
		try {
			Product product = productsRepository.findById(id).orElse(null);
			if (product != null) {
				// Remove the image reference from the product
				Image image = product.getImageId();
				if (image != null) {
					product.setImageId(image);
					productsRepository.save(product); // Save changes to product
					// Delete the image if no other products reference it
					if (imageRepository.findProductsByImageId(image.getImageId()).isEmpty()) {
						imageRepository.delete(image);
					}
				}
				// Delete the product
				productsRepository.delete(product);

				redirectAttributes.addFlashAttribute("successMessage", "Xóa sản phầm thành công!.");
			} else {
				// Add failure toast message
				redirectAttributes.addFlashAttribute("errorMessage", "Xóa sản phầm thất bại!.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi trong quá trình xóa sản phẩm!.");

		}
		return "redirect:/seller/products/list";

	}

	@PostMapping("/reset")
	public String resetProducts(HttpServletRequest req, Model model) {
		// Khởi tạo danh sách sản phẩm và danh mục
		List<Product> products = productsRepository.findAll();
		model.addAttribute("pageProd", products);
		List<Category> categories = categoryRepository.findAll();
		model.addAttribute("categories", categories);

		// Khởi tạo itemProd để tránh lỗi NullPointerException
		Product itemProd = new Product(); // Hoặc khởi tạo với các giá trị mặc định cần thiết
		model.addAttribute("itemProd", itemProd);

		// Chỉ định view
		req.setAttribute("view", "/Seller/QuanLySanPham/add.html");
		return "indexSeller";
	}

}
