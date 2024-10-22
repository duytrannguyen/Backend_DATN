package com.poly.Controller.Client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.poly.Model.CartItem;
import com.poly.Model.User;
import com.poly.Service.CartService;

@RestController
@RequestMapping("/api/pet/cart")
public class Client_CartControllerAPI {
    @Autowired
    private CartService cartService;

    // API thêm sản phẩm vào giỏ hàng
    @PostMapping("/add/{productId}")
    public ResponseEntity<String> addToCart(
            @PathVariable Integer productId,
            @RequestBody CartItem cartItem) {
        try {
            Integer userId = cartItem.getUser().getUsersId(); // Lấy userId từ cartItem
            // Gọi service để thêm sản phẩm vào giỏ hàng
            cartService.addProductToCart(userId, productId, cartItem.getQuantity());
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    // Xem giỏ hàng của người dùng
    @GetMapping("/view/{userId}")
    public ResponseEntity<Map<String, Object>> viewCart(@PathVariable Integer userId) {
        try {
            // Lấy giỏ hàng dựa vào userId từ dịch vụ
            Map<String, Object> response = cartService.viewCartByUserId(userId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

    // API xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Integer cartItemId) {
        try {
            // Gọi service để xóa sản phẩm khỏi giỏ hàng
            cartService.removeCartItem(cartItemId);
            return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Mục giỏ hàng không tồn tại: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    // API cập nhật sản phẩm trong giỏ hàng
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<Map<String, Object>> updateCartItem(
            @PathVariable Integer cartItemId,
            @RequestBody CartItem updatedCartItem) {
        try {
            // Gọi service để cập nhật sản phẩm trong giỏ hàng
            cartService.updateCartItem(cartItemId, updatedCartItem);
            // Tính toán tổng số lượng và tổng giá tiền cho giỏ hàng
            User userId = updatedCartItem.getUser(); // Lấy userId từ cartItem
            Map<String, Double> totals = cartService.updateCartTotals(userId);
            // Tạo phản hồi trả về
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Sản phẩm trong giỏ hàng đã được cập nhật.");
            response.put("totals", totals);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Mục giỏ hàng không tồn tại: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Có lỗi xảy ra: " + e.getMessage()));
        }
    }

}
