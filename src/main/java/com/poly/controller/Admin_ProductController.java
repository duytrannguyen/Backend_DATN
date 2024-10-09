package com.poly.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

import com.poly.model.Category;
import com.poly.model.Image;
import com.poly.model.Product;
import com.poly.model.ProductStatus;
import com.poly.repository.CategoryRepository;
import com.poly.repository.ImageRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ProductStatusRepository;
import com.poly.repository.UserRepository;
import com.poly.service.CategoryService;
import com.poly.service.ImageService;
import com.poly.service.ProductService;
import com.poly.service.ProductStatusService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("admin/products")
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
		// Sắp xếp theo ID giảm dần
		Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());

		Page<Product> productPage = productsRepository.findAll(pageable);

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
		req.setAttribute("view", "/admin/QuanLySanPham/Products.html");
		return "indexAdmin";
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

		req.setAttribute("view", "/admin/QuanLySanPham/add.html");
		return "indexAdmin";
	}

	@PostMapping("/create")
	public String createProduct(Model model, HttpServletRequest req, 
	                            @RequestParam("img") MultipartFile photo,
	                            @RequestParam("productName") String productName, 
	                            @RequestParam("price") String priceStr,
	                            @RequestParam("discountPercentage") String discountPercentageStr,
	                            @RequestParam("publishingYear") String publishingYearStr, 
	                            @RequestParam("weight") String weight,
	                            @RequestParam("size") String size, 
	                            @RequestParam("numberOfPages") String numberOfPagesStr,
	                            @RequestParam("language") String language, 
	                            @RequestParam("author") String author,
	                            @RequestParam("description") String description, 
	                            @RequestParam("manufacturer") String manufacturer,
	                            @RequestParam("postingDate") String postingDateStr,
	                            @RequestParam(value = "quantity", required = false, defaultValue = "0") String quantityStr,
	                            @RequestParam("categoryId") String categoryIdStr, 
	                            @RequestParam("statusId") String statusIdStr,
	                            RedirectAttributes redirectAttributes) {

	    List<String> errors = new ArrayList<>();

	    // Validate required fields
	    if (productName == null || productName.trim().isEmpty()) {
	        errors.add("Tên sản phẩm là bắt buộc.");
	    }
	    if (priceStr == null || priceStr.trim().isEmpty()) {
	        errors.add("Giá sản phẩm là bắt buộc.");
	    }
	    if (publishingYearStr == null || publishingYearStr.trim().isEmpty()) {
	        errors.add("Năm xuất bản là bắt buộc.");
	    }
	    if (weight == null || weight.trim().isEmpty()) {
	        errors.add("Trọng lượng là bắt buộc.");
	    }
	    if (size == null || size.trim().isEmpty()) {
	        errors.add("Kích thước là bắt buộc.");
	    }
	    if (numberOfPagesStr == null || numberOfPagesStr.trim().isEmpty()) {
	        errors.add("Số trang là bắt buộc.");
	    }
	    if (language == null || language.trim().isEmpty()) {
	        errors.add("Ngôn ngữ là bắt buộc.");
	    }
	    if (author == null || author.trim().isEmpty()) {
	        errors.add("Tác giả là bắt buộc.");
	    }
	    if (description == null || description.trim().isEmpty()) {
	        errors.add("Mô tả là bắt buộc.");
	    }
	    if (manufacturer == null || manufacturer.trim().isEmpty()) {
	        errors.add("Nhà sản xuất là bắt buộc.");
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
	    
	    // Validate discountPercentage
	    float discountPercentage = 0;
	    try {
	        discountPercentage = Float.parseFloat(discountPercentageStr);
	        if (discountPercentage < 0) {
	            errors.add("Phần trăm giảm giá không được âm.");
	        }
	    } catch (NumberFormatException e) {
	        errors.add("Định dạng phần trăm giảm giá không hợp lệ.");
	    }

	    // Validate publishingYear
	    int publishingYear = 0;
	    try {
	        publishingYear = Integer.parseInt(publishingYearStr);
	        if (publishingYear <= 0) {
	            errors.add("Năm xuất bản phải là số nguyên dương.");
	        }
	    } catch (NumberFormatException e) {
	        errors.add("Định dạng năm xuất bản không hợp lệ.");
	    }

	    // Validate numberOfPages
	    int numberOfPages = 0;
	    try {
	        numberOfPages = Integer.parseInt(numberOfPagesStr);
	        if (numberOfPages <= 0) {
	            errors.add("Số trang phải là số nguyên dương.");
	        }
	    } catch (NumberFormatException e) {
	        errors.add("Định dạng số trang không hợp lệ.");
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

	 // Check if product with the same name already exists
	    if (productService.existsByProductName(productName)) {
	        errors.add("Sản phẩm với tên '" + productName + "' đã tồn tại trong hệ thống.");
	    }

	    // Check for errors
	    if (!errors.isEmpty()) {
	        // Return to the creation page with the errors
	        redirectAttributes.addFlashAttribute("errorMessage", String.join(", ", errors));
	        return "redirect:/admin/products/create"; // Redirect back to the product creation page
	    }

	    try {
	        // Tìm đối tượng Category và Status theo mã
	        Category category = categoryService.findByCategoryCode(categoryId);
	        ProductStatus status = productStatusService.findByStatusId(statusId);

	        // Tạo đối tượng Product mới
	        Product product = new Product();
	        product.setProductName(productName);
	        product.setPrice(price);
	        product.setDiscountPercentage(discountPercentage);
	        product.setPublishingYear(publishingYear);
	        product.setWeight(weight);
	        product.setSize(size);
	        product.setNumberOfPages(numberOfPagesStr);
	        product.setLanguage(language);
	        product.setAuthor(author);
	        product.setDescription(description);
	        product.setManufacturer(manufacturer);
	        product.setPostingDate(postingDate);
	        product.setQuantity(quantity);
	        product.setCategory(category);
	        product.setStatus(status);

	        // Xử lý ảnh
	        if (!photo.isEmpty()) {
	            String fileName = photo.getOriginalFilename();
	            String realPath = req.getServletContext().getRealPath("/Image_SP/" + fileName);
	            Path path = Path.of(realPath);
	            if (!Files.exists(path.getParent())) {
	                Files.createDirectories(path.getParent());
	            }
	            File file = new File(realPath);
	            photo.transferTo(file);

	            // Tạo đối tượng Image mới và lưu thông tin ảnh vào cơ sở dữ liệu
	            Image image = new Image();
	            image.setImageName(fileName);
	            imageService.saveImage(image);
	            product.setImageId(image);
	        }

	        // Lưu sản phẩm vào cơ sở dữ liệu
	        productService.saveProduct(product);
	        redirectAttributes.addFlashAttribute("successMessage", "Tạo sản phẩm thành công!");

	    } catch (Exception e) {
	        errors.add("Đã xảy ra lỗi: " + e.getMessage());
	        redirectAttributes.addFlashAttribute("errorMessage", String.join(", ", errors));
	        return "redirect:/admin/products/create"; // Chuyển hướng về trang tạo sản phẩm
	    }

	    return "redirect:/admin/products/list"; // Chuyển hướng đến danh sách sản phẩm
	}

	@GetMapping("/edit/{productId}")
	public String edit(HttpServletRequest req, Model model, @PathVariable(name = "productId") Integer id) {
		// Tìm sản phẩm theo id
		Product item = productsRepository.findById(id).orElse(null);
		if (item == null) {
			// Xử lý trường hợp sản phẩm không được tìm thấy
			return "redirect:/admin/products/list?error=ProductNotFound";
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
		req.setAttribute("view", "/admin/QuanLySanPham/edit.html");
		return "indexAdmin";
	}

	@PostMapping("/update/{productId}")
	public String updateProduct(Model model, @PathVariable(name = "productId") Integer productId,
			@RequestParam("productName") String productName, @RequestParam("price") String priceStr,
			@RequestParam("discountPercentage") String discountPercentageStr,
			@RequestParam("publishingYear") String publishingYearStr, @RequestParam("weight") String weight,
			@RequestParam("size") String size, @RequestParam("numberOfPages") String numberOfPagesStr,
			@RequestParam("language") String language, @RequestParam("author") String author,
			@RequestParam("description") String description, @RequestParam("manufacturer") String manufacturer,
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
		float discountPercentage = 0;
		try {
			discountPercentage = Float.parseFloat(discountPercentageStr);
			if (discountPercentage == 0) {
				errors.add("Sản phẩm không được giảm giá.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng giá không hợp lệ.");
		}
		// Validate publishingYear
		int publishingYear = 0;
		try {
			publishingYear = Integer.parseInt(publishingYearStr);
			if (publishingYear <= 0) {
				errors.add("Năm xuất bản phải là số nguyên dương.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng năm xuất bản không hợp lệ.");
		}

		// Validate numberOfPages
		int numberOfPages = 0;
		try {
			numberOfPages = Integer.parseInt(numberOfPagesStr);
			if (numberOfPages <= 0) {
				errors.add("Số trang phải là số nguyên dương.");
			}
		} catch (NumberFormatException e) {
			errors.add("Định dạng số trang không hợp lệ.");
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

		// Check for errors
		if (!errors.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", String.join(", ", errors));
			return "redirect:/admin/products/update/" + productId; // Chuyển hướng về trang cập nhật sản phẩm
		}

		try {
			// Find Product by productId
			Product product = productService.findByProductId(productId);
			if (product == null) {
				redirectAttributes.addFlashAttribute("errorMessage", "Sản phẩm không tồn tại.");
				return "redirect:/admin/products/list";
			}

			// Find Category and Status by their IDs
			Category category = categoryService.findByCategoryCode(categoryId);
			ProductStatus status = productStatusService.findByStatusId(statusId);

			// Update product attributes
			product.setProductName(productName);
			product.setPrice(price);
			product.setDiscountPercentage(discountPercentage);
			product.setPublishingYear(publishingYear);
			product.setWeight(weight);
			product.setSize(size);
			product.setNumberOfPages(numberOfPagesStr);
			product.setLanguage(language);
			product.setAuthor(author);
			product.setDescription(description);
			product.setManufacturer(manufacturer);
			product.setPostingDate(postingDate);
			product.setQuantity(quantity);
			product.setCategory(category);
			product.setStatus(status); // Set the status for the product

			// Handle image upload if provided
			if (photo != null && !photo.isEmpty()) {
				String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
				String uploadDir = "/Image_SP/";
				String realPath = request.getServletContext().getRealPath(uploadDir);
				Path path = Paths.get(realPath);
				if (Files.notExists(path)) {
					Files.createDirectories(path);
				}

				// Save image file
				Path filePath = Paths.get(realPath, fileName);
				Files.copy(photo.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

				// Create new Image object and save image details to database
				Image image = new Image();
				image.setImageName(fileName);
				imageService.saveImage(image);

				// Link Image object to Product before saving Product
				product.setImageId(image);
			}

			// Save updated product to database
			productService.saveProduct(product);

			// Add success message
			redirectAttributes.addFlashAttribute("successMessage", "Cập nhật sản phẩm thành công!");

		} catch (IOException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", "Đã xảy ra lỗi: " + e.getMessage());
			return "redirect:/admin/products/update/" + productId; // Chuyển hướng về trang cập nhật sản phẩm
		}

		// Redirect to the list of products
		return "redirect:/admin/products/list";
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
		return "redirect:/admin/products/list";

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
		req.setAttribute("view", "/admin/QuanLySanPham/add.html");
		return "indexAdmin";
	}

}