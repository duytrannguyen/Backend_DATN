package com.poly.Controller.Seller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.poly.dto.ProductDTO;
import com.poly.Model.Product;
import com.poly.Model.Seller;
import com.poly.Model.User;
import com.poly.Service.InvoiceService;
import com.poly.Service.ProductService;
import com.poly.Service.SellerService;
import com.poly.Service.UserService;

@RestController // Sử dụng RestController để trả về JSON
@RequestMapping("/api/pet/seller/report")
public class Seller_ReportController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/view/{sellerId}")
    public ResponseEntity<Map<String, Object>> getReport(@PathVariable int sellerId) {
        Map<String, Object> reportData = new HashMap<>();

        // Kiểm tra seller tồn tại
        Seller seller = sellerService.findSellerById(sellerId);
        if (seller == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "Seller not found"));
        }

        // Lấy dữ liệu báo cáo cho seller
        reportData.put("totalProducts", productService.getTotalProductsBySeller(sellerId));
        reportData.put("totalOrders", invoiceService.getTotalOrdersBySeller(sellerId));
        reportData.put("totalAmount", invoiceService.getTotalAmountBySeller(sellerId));
        reportData.put("products", productService.getAllProductsBySeller(sellerId));

        // Trả về báo cáo
        return ResponseEntity.ok(reportData);
    }

    @GetMapping("/topselling/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getTop3BestSellingProductsBySellerId(@PathVariable int sellerId) {
        List<ProductDTO> topProducts = productService.getTop3BestSellingProductsBySellerId(sellerId);
        return ResponseEntity.ok(topProducts);
    }

}
