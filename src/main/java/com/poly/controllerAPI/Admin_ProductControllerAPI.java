package com.poly.controllerAPI;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.poly.model.Category;
import com.poly.model.Image;
import com.poly.model.Product;
import com.poly.model.ProductStatus;
import com.poly.repository.CategoryRepository;
import com.poly.repository.ImageRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ProductStatusRepository;
import com.poly.service.CategoryService;
import com.poly.service.ImageService;
import com.poly.service.ProductService;
import com.poly.service.ProductStatusService;

import Exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/products")
public class Admin_ProductControllerAPI {

    @Autowired
    private ProductRepository productsRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private ProductStatusRepository productStatusRepository;
    @Autowired
    private ProductStatusService productStatusService;

    @GetMapping("/list")
    public ResponseEntity<Page<Product>> listProducts(
            @RequestParam(name = "pageNo", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("productId").descending());
        Page<Product> productPage = productsRepository.findAll(pageable);

        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }
//    public List<Product> getAllProducts() {
//        return productsRepository.findAll();
//    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/statuses")
    public ResponseEntity<List<ProductStatus>> getProductStatuses() {
        List<ProductStatus> statuses = productStatusRepository.findAll();
        return ResponseEntity.ok(statuses);
    }
    
    @GetMapping("/form")
    public ResponseEntity<?> formProducts() {
        List<Category> categories = categoryRepository.findAll();
        List<ProductStatus> productStatuses = productStatusRepository.findAll();
        return ResponseEntity.ok(new ProductFormResponse(categories, productStatuses, new Product()));
    }
    // xử lí hình ảnh
    private String saveImage(MultipartFile photo) throws IOException {
        if (!photo.isEmpty()) {
            // Lấy tên file
            String fileName = photo.getOriginalFilename();
            // Đường dẫn lưu file
            String realPath = "D:\\DuAnTotNhiep\\WebTMDTBook\\src\\main\\resources\\static\\Image_SP\\" + fileName;
            Path path = Path.of(realPath);
            // Tạo thư mục nếu chưa tồn tại
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            // Lưu file
            File file = new File(realPath);
            photo.transferTo(file);
            return fileName; // Trả về tên file để sử dụng
        }
        return null;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createProduct(
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
            @RequestParam("statusId") String statusIdStr) {

        List<String> errors = validateProduct(productName, priceStr, discountPercentageStr, publishingYearStr, weight, size, numberOfPagesStr, language, author, description, manufacturer, postingDateStr, quantityStr, categoryIdStr, statusIdStr);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join(", ", errors));
        }

        try {
            Category category = categoryService.findByCategoryCode(Integer.parseInt(categoryIdStr));
            ProductStatus status = productStatusService.findByStatusId(Integer.parseInt(statusIdStr));

            Product product = new Product();
            setProductFields(product, productName, priceStr, discountPercentageStr, publishingYearStr, weight, size, numberOfPagesStr, language, author, description, manufacturer, postingDateStr, quantityStr, category, status);

            // Xử lý lưu ảnh
            String fileName = saveImage(photo);
            if (fileName != null) {
                Image image = new Image();
                image.setImageName(fileName);
                imageService.saveImage(image);
                product.setImageId(image);
            }

            productService.saveProduct(product);
            return ResponseEntity.ok("Tạo sản phẩm thành công!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    @GetMapping("/edit/{productId}")
    public ResponseEntity<?> edit(@PathVariable Integer productId) {
        Product product = productsRepository.findById(productId).orElse(null);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sản phẩm không tìm thấy.");
        }

        List<Category> categories = categoryRepository.findAll();
        List<ProductStatus> productStatuses = productStatusRepository.findAll();
        return ResponseEntity.ok(new ProductEditResponse(product, categories, productStatuses));
    }

    @PutMapping("/update/{id}")
    public Product updatePoroduct(@PathVariable int id, @RequestBody Product product) {
		Product product2 = productsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht id " + id));
		product2.setProductId(product.getProductId());
		product2.setProductName(product.getProductName());
		product2.setDiscountPercentage(product.getDiscountPercentage());
		product2.setPrice(product.getPrice());
		product2.setPublishingYear(product.getPublishingYear());
		product2.setWeight(product.getWeight());
		product2.setSize(product.getSize());
		product2.setNumberOfPages(product.getNumberOfPages());
		product2.setAuthor(product.getAuthor());
		product2.setDescription(product.getDescription());
		product2.setManufacturer(product.getManufacturer());
		product2.setPostingDate(product.getPostingDate());
		product2.setCategory(product.getCategory());
		product2.setImageId(product.getImageId());
		product2.setStatus(product.getStatus());
		return productsRepository.save(product2);
	}

    @DeleteMapping("/delete/{id}")
    public void deleteProduct(@PathVariable int id) {
		Product product = productsRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found wiht id " + id));
		System.out.println(">>> " +id);
		 productsRepository.delete(product);
	}
    private List<String> validateProduct(String productName, String priceStr, String discountPercentageStr, String publishingYearStr, String weight, String size, String numberOfPagesStr, String language, String author, String description, String manufacturer, String postingDateStr, String quantityStr, String categoryIdStr, String statusIdStr) {
        List<String> errors = new ArrayList<>();
        if (productName.isEmpty()) errors.add("Tên sản phẩm không được để trống.");
        if (priceStr.isEmpty() || !isNumeric(priceStr)) errors.add("Giá không hợp lệ.");
        if (discountPercentageStr.isEmpty() || !isNumeric(discountPercentageStr)) errors.add("Phần trăm giảm giá không hợp lệ.");
        if (publishingYearStr.isEmpty() || !isNumeric(publishingYearStr)) errors.add("Năm xuất bản không hợp lệ.");
        if (weight.isEmpty()) errors.add("Cân nặng không được để trống.");
        if (size.isEmpty()) errors.add("Kích thước không được để trống.");
        if (numberOfPagesStr.isEmpty() || !isNumeric(numberOfPagesStr)) errors.add("Số trang không hợp lệ.");
        if (language.isEmpty()) errors.add("Ngôn ngữ không được để trống.");
        if (author.isEmpty()) errors.add("Tác giả không được để trống.");
        if (description.isEmpty()) errors.add("Mô tả không được để trống.");
        if (manufacturer.isEmpty()) errors.add("Nhà sản xuất không được để trống.");
        if (postingDateStr.isEmpty() || !isDate(postingDateStr)) errors.add("Ngày đăng không hợp lệ.");
        if (quantityStr.isEmpty() || !isNumeric(quantityStr)) errors.add("Số lượng không hợp lệ.");
        if (categoryIdStr.isEmpty() || !isNumeric(categoryIdStr)) errors.add("ID loại sản phẩm không hợp lệ.");
        if (statusIdStr.isEmpty() || !isNumeric(statusIdStr)) errors.add("ID trạng thái không hợp lệ.");
        return errors;
    }

    private void setProductFields(Product product, String productName, String priceStr, String discountPercentageStr, String publishingYearStr, String weight, String size, String numberOfPagesStr, String language, String author, String description, String manufacturer, String postingDateStr, String quantityStr, Category category, ProductStatus status) throws ParseException {
        product.setProductName(productName);
        product.setPrice(Float.parseFloat(priceStr));
        product.setDiscountPercentage(Float.parseFloat(discountPercentageStr));
        product.setPublishingYear(Integer.parseInt(publishingYearStr));
        product.setWeight(weight);
        product.setSize(size);
        product.setNumberOfPages(numberOfPagesStr);
        product.setLanguage(language);
        product.setAuthor(author);
        product.setDescription(description);
        product.setManufacturer(manufacturer);
        product.setPostingDate(new SimpleDateFormat("yyyy-MM-dd").parse(postingDateStr));
        product.setQuantity(Integer.parseInt(quantityStr));
        product.setCategory(category);
        product.setStatus(status);
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private boolean isDate(String str) {
        try {
            new SimpleDateFormat("yyyy-MM-dd").parse(str);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    // Response DTOs
    static class ProductFormResponse {
        private List<Category> categories;
        private List<ProductStatus> productStatuses;
        private Product product;

        public ProductFormResponse(List<Category> categories, List<ProductStatus> productStatuses, Product product) {
            this.categories = categories;
            this.productStatuses = productStatuses;
            this.product = product;
        }

        // Getters and Setters
    }

    static class ProductEditResponse {
        private Product product;
        private List<Category> categories;
        private List<ProductStatus> productStatuses;

        public ProductEditResponse(Product product, List<Category> categories, List<ProductStatus> productStatuses) {
            this.product = product;
            this.categories = categories;
            this.productStatuses = productStatuses;
        }

        // Getters and Setters
    }
}
