package com.poly.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.poly.model.Category;
import com.poly.model.Invoice;
import com.poly.model.Product;
import com.poly.repository.CartItemRepository;
import com.poly.repository.CategoryRepository;
import com.poly.repository.ImageRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ShoppingCartRepository;
import com.poly.service.CategoryService;
import com.poly.service.InvoiceService;
import com.poly.service.ProductService;

@Controller
@RequestMapping("home")
public class Client_HomeController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;

    // >>>>>>> update_Code
    @GetMapping("/index")
    public String home(Model model) {
        List<Product> products = productService.getAllProducts(); // Lấy tất cả sản phẩm
        model.addAttribute("products", products); // Thêm danh sách sản phẩm vào mô hình

        // Tạo danh sách để phân loại sản phẩm hiện tại và sản phẩm sắp bán
        List<Product> currentProducts = new ArrayList<>(); // Danh sách sản phẩm hiện tại
        List<Product> upcomingProducts = new ArrayList<>(); // Danh sách sản phẩm sắp bán
        Date currentDate = new Date(); // Lấy ngày hiện tại

        // Duyệt qua từng sản phẩm để phân loại
        for (Product product : products) {
            // Kiểm tra nếu ngày đăng nhỏ hơn ngày hiện tại
            if (product.getPostingDate().before(currentDate)) {
                currentProducts.add(product); // Sản phẩm có sẵn
            } else {
                upcomingProducts.add(product); // Sản phẩm sắp bán
            }
        }

        // Thêm cả hai danh sách vào mô hình để có thể sử dụng trong view
        model.addAttribute("currentProducts", currentProducts);
        model.addAttribute("upcomingProducts", upcomingProducts);
        // Chỉ lấy danh mục loại sàn phẩm ở trạng thấy On
        List<Category> categories = categoryRepository.findAllCategoriesStatusId1();
        model.addAttribute("categories", categories); // Thêm danh sách danh mục vào mô hình

        return "indexClient"; // Trả về view indexClient
    }
    @GetMapping("/products1")
    @ResponseBody
    public String products1() {
    	return "abc";
    }
    @GetMapping("/products")
    public String products(Model model,
            @RequestParam(name = "keyName", required = false) String keyName,
            @RequestParam("pageNo") Optional<Integer> pageNo,
            @RequestParam(name = "categoryId", required = false) Integer categoryId) {

        int pageSize = 6; // Số sản phẩm trên mỗi trang
        Sort sort = Sort.by(Sort.Direction.DESC, "productId");
        Pageable pageable = PageRequest.of(pageNo.orElse(0), pageSize, sort);

        Page<Product> page;
        if (StringUtils.hasText(keyName)) {
            page = productRepository.findByProductNameContaining(keyName, pageable);
        } else if (categoryId != null) {
            page = productRepository.findProductsByCategory(categoryId, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }
        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Lọc sản phẩm theo ngày đăng
        List<Product> filteredProducts = page.getContent().stream()
                .filter(product -> {
                    LocalDate postingDate = new java.sql.Date(product.getPostingDate().getTime()).toLocalDate();
                    return !postingDate.isAfter(currentDate); // Sản phẩm có ngày đăng nhỏ hơn hoặc bằng ngày hiện tại
                })
                .collect(Collectors.toList());
        int totalPages = page.getTotalPages(); // Tổng số trang
        int currentPage = pageNo.orElse(0); // Trang hiện tại

        // List<Product> products = page.getContent();
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "categoryId"));

        List<Double> discountedPrices = new ArrayList<>();
        for (Product product : filteredProducts) {
            double discountedPrice = product.getPrice()
                    - ((product.getPrice() * product.getDiscountPercentage()) / 100);
            discountedPrices.add(discountedPrice);
        }

        if (filteredProducts.isEmpty()) {
            model.addAttribute("noProductsFound", true);
        } else {
            model.addAttribute("products", filteredProducts);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("totalPageProduct", totalPages);
        model.addAttribute("pageProduct", page);
        model.addAttribute("pageClick", currentPage);
        model.addAttribute("products", filteredProducts);
        model.addAttribute("discountedPrices", discountedPrices);
        model.addAttribute("selectedCategoryId", categoryId);

        return "client/Product";
    }

    // >>>>>>> update_Code

    @PostMapping("/products")
    public String priceProducts(Model model,
            @RequestParam("pageNo") Optional<Integer> pageNo,
            @RequestParam(name = "categoryId", required = false) Integer categoryId,
            @RequestParam(name = "productId", required = false) Integer productId,
            @RequestParam("price_range") Optional<String> priceRange) {

        int pageSize = 6; // Số sản phẩm trên mỗi trang
        Sort sort = Sort.by(Direction.DESC, "productId");
        Pageable pageable = PageRequest.of(pageNo.orElse(0), pageSize, sort);

        Page<Product> page;
        if (priceRange.isPresent()) {
            String[] prices = priceRange.get().split("-");
            double minPrice = Double.parseDouble(prices[0]);
            double maxPrice = Double.parseDouble(prices[1]);

            page = productRepository.findByPriceBetween(minPrice, maxPrice, pageable);
        } else if (categoryId != null) {
            page = productRepository.findProductsByCategory(categoryId, pageable);
        } else {
            page = productRepository.findAll(pageable);
        }

        // Lấy ngày hiện tại
        LocalDate currentDate = LocalDate.now();

        // Lọc sản phẩm theo ngày đăng
        List<Product> filteredProducts = page.getContent().stream()
                .filter(product -> {
                    LocalDate postingDate = new java.sql.Date(product.getPostingDate().getTime()).toLocalDate();
                    return !postingDate.isAfter(currentDate); // Sản phẩm có ngày đăng nhỏ hơn hoặc bằng ngày hiện tại
                })
                .collect(Collectors.toList());

        int totalPages = page.getTotalPages(); // Tổng số trang
        int currentPage = pageNo.orElse(0); // Trang hiện tại

        List<Category> categories = categoryRepository.findAll(Sort.by(Direction.DESC, "categoryId"));

        List<Double> discountedPrices = new ArrayList<>();
        for (Product product : filteredProducts) {
            double discountedPrice = product.getPrice()
                    - ((product.getPrice() * product.getDiscountPercentage()) / 100);
            discountedPrices.add(discountedPrice);
        }

        if (filteredProducts.isEmpty()) {
            model.addAttribute("noProductsFound", true);
        } else {
            model.addAttribute("products", filteredProducts);
        }

        model.addAttribute("categories", categories);
        model.addAttribute("totalPageProduct", totalPages); // Tổng số trang
        model.addAttribute("pageProduct", page); // Truyền đối tượng Page<Product> vào mô hình
        model.addAttribute("pageClick", currentPage);
        model.addAttribute("discountedPrices", discountedPrices);
        model.addAttribute("selectedCategoryId", categoryId);

        return "client/Product";
    }

    // <<<<<<< khagdn

    // @GetMapping("/products/details/{productId}")
    // public String productDetails(Model model, @PathVariable("productId") Integer
    // productId,
    // @RequestParam("pageNo") Optional<Integer> pageNo) {
    // =======

    @GetMapping("/products/details/{productId}")
    public String productDetails(Model model, @PathVariable("productId") Integer productId,
            @RequestParam("pageNo") Optional<Integer> pageNo) {

        try {
            // Lấy sản phẩm chi tiết
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
            model.addAttribute("product", product);

            // Tính giá sau khi giảm

            double discountedPrice = product.getPrice()
                    - ((product.getPrice() * product.getDiscountPercentage()) / 100);
            model.addAttribute("discountedPrice", discountedPrice);

            // Phân trang danh sách sản phẩm
            Sort sort = Sort.by(Sort.Direction.DESC, "productId");
            Pageable pageable = PageRequest.of(pageNo.orElse(0), 4, sort);
            Page<Product> page = productRepository.findAll(pageable);

            List<Integer> totalPages = new ArrayList<>();
            for (int i = 0; i < page.getTotalPages(); i++) {
                totalPages.add(i + 1);
            }

            List<Product> products = page.getContent();

            List<Double> discountedPrices = new ArrayList<>();
            for (Product productss : products) {
                double discountedPrice1 = productss.getPrice()
                        - ((productss.getPrice() * productss.getDiscountPercentage()) / 100);
                discountedPrices.add(discountedPrice1);
            }

            model.addAttribute("totalPageProduct", totalPages);
            model.addAttribute("pageProduct", page);
            model.addAttribute("pageClick", pageNo.orElse(0));
            model.addAttribute("products", products);
            model.addAttribute("discountedPrices", discountedPrices);

            return "client/ProductDetails";

        } catch (Exception e) {
            // Ghi log lỗi
            e.printStackTrace();
            return "error"; // Trả về trang lỗi nếu xảy ra ngoại lệ
        }
    }
    // >>>>>>> update_Code

    // try {
    // // Lấy sản phẩm chi tiết
    // Product product = productRepository.findById(productId)
    // .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" +
    // productId));
    // model.addAttribute("product", product);
    //
    // // Tính giá sau khi giảm
    //
    // double discountedPrice = product.getPrice()
    // - ((product.getPrice() * product.getDiscountPercentage()) / 100);
    // model.addAttribute("discountedPrice", discountedPrice);
    //
    // // Phân trang danh sách sản phẩm
    // Sort sort = Sort.by(Sort.Direction.DESC, "productId");
    // Pageable pageable = PageRequest.of(pageNo.orElse(0), 4, sort);
    // Page<Product> page = productRepository.findAll(pageable);
    //
    // List<Integer> totalPages = new ArrayList<>();
    // for (int i = 0; i < page.getTotalPages(); i++) {
    // totalPages.add(i + 1);
    // }
    //
    // List<Product> products = page.getContent();
    //
    // model.addAttribute("totalPageProduct", totalPages);
    // model.addAttribute("pageProduct", page);
    // model.addAttribute("pageClick", pageNo.orElse(0));
    // model.addAttribute("products", products);
    //
    // return "client/ProductDetails";
    //
    // } catch (Exception e) {
    // // Ghi log lỗi
    // e.printStackTrace();
    // return "error"; // Trả về trang lỗi nếu xảy ra ngoại lệ
    // }

    // @GetMapping("products/details/cart")
    // public String Cart(Model model) {
    //
    // List<CartItem> cartItemts =
    // cartItemRepository.findAll(Sort.by(Direction.DESC, "cartItemId"));
    // model.addAttribute("cartItemts", cartItemts);
    //
    // return "client/Cart";
    // }

    @GetMapping("/products/details/cart/pay")
    public String Pay() {
        return "client/Pay";
    }

    @GetMapping("/about")
    public String about() {
        return "client/About";
    }

    @GetMapping("/contact")
    public String Contact() {
        return "client/Contact";
    }

    @GetMapping("/history")
    public String History(Model model) {
        List<Invoice> invoices = invoiceService.getAllInvoices();
        invoices.sort((i1, i2) -> i2.getInvoiceId().compareTo(i1.getInvoiceId()));
        model.addAttribute("invoices", invoices);
        return "client/History";
    }

}
