package com.poly.Controller.Client;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Mapper.ImageMapper;
import com.poly.Mapper.ProductMapper;
import com.poly.Model.Image;
import com.poly.Model.Product;
import com.poly.Reponsitory.ImageRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Service.ProductService;
import com.poly.dto.ImageDTO;
import com.poly.dto.ProductDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/home")
public class Client_ProductController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductService productService;
    @Autowired
    private ImageRepository imageRepository;

    // Hiển thị tất cả sản phẩm
    @GetMapping("/products/all")
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productService.getAllProducts(); // Lấy danh sách sản phẩm từ service
        return products.stream().map(ProductMapper::toDTO).collect(Collectors.toList()); // Chuyển đổi sang DTO
    }

    // Hiển thị sản phẩm theo id
    @GetMapping("/products/{id}")
    public ProductDTO getProductById(@PathVariable int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id " + id));
        return ProductMapper.toDTO(product);
    }

    // Hiển thị sản phẩm theo ngày đăng bán
    @GetMapping("/products/postingDate")
    public Map<String, List<ProductDTO>> getPostingDateProducts() {
        List<Product> products = productService.getAllProducts();

        List<ProductDTO> currentProducts = new ArrayList<>();
        List<ProductDTO> upcomingProducts = new ArrayList<>();
        Date currentDate = new Date();

        for (Product product : products) {
            if (product.getPostingDate().before(currentDate) || product.getPostingDate().equals(currentDate)) {
                currentProducts.add(ProductMapper.toDTO(product));
            } else {
                upcomingProducts.add(ProductMapper.toDTO(product));
            }
        }

        Map<String, List<ProductDTO>> response = new HashMap<>();
        response.put("currentProducts", currentProducts);
        response.put("upcomingProducts", upcomingProducts);

        return response;
    }

    // Lọc và tìm kiếm sản phẩm
    @GetMapping("/products/page")
    public Map<String, Object> getMap(@RequestParam(name = "keyName", required = false) String keyName,
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "productId", required = false) Integer productId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Product> productPage;

        if (StringUtils.hasText(keyName)) {
            productPage = productRepository.findByProductNameContaining(keyName, pageable);
        } else if (categoryId != null) {
            productPage = productRepository.findProductsByCategory(categoryId, pageable);
        } else {
            productPage = productRepository.findAll(pageable);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("products",
                productPage.getContent().stream().map(ProductMapper::toDTO).collect(Collectors.toList()));
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return response;
    }

    @GetMapping("/products/{productId}/images")
    public ResponseEntity<List<ImageDTO>> getImagesByProductId(@PathVariable Integer productId) {
        List<Image> images = imageRepository.findByProductId(productId);

        // Kiểm tra nếu không có ảnh nào cho sản phẩm
        if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        // Chuyển đổi danh sách Image sang ImageDTO
        List<ImageDTO> imageDTOs = images.stream()
                .map(ImageMapper::toDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(imageDTOs, HttpStatus.OK);
    }
}
