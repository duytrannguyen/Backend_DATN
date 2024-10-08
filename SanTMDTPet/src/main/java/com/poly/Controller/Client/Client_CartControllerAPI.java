package com.poly.Controller.Client;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poly.Model.CartItem;
import com.poly.Model.Product;
import com.poly.Model.ShoppingCart;
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
            @RequestBody CartItem cartItem) { // Sử dụng CartItem từ request body
        try {
            Integer userId = cartItem.getShoppingCart().getUser().getUsersId(); // Lấy userId từ cartItem
            // Gọi service để thêm sản phẩm vào giỏ hàng
            cartService.addProductToCart(userId, productId, cartItem.getQuantity());
            return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    // xem giỏ hàng của người dùng
    @GetMapping("/view/{userId}") // Sử dụng @PathVariable cho userId
    public ResponseEntity<Map<String, Object>> viewCart(@PathVariable("userId") Integer userId) {
        try {
            // Lấy giỏ hàng dựa vào userId
            ShoppingCart shoppingCart = cartService.getCartByUserId(userId);
            // Tính tổng giá tiền
            double totalPrice = shoppingCart.getCartItems().stream()
                    .mapToDouble(cartItem -> cartItem.getProductId().getPrice() * cartItem.getQuantity())
                    .sum();
            // Tạo một Map để trả về giỏ hàng và tổng giá
            Map<String, Object> response = new HashMap<>();
            response.put("shoppingCart", shoppingCart);
            response.put("totalPrice", totalPrice);
            // Trả về giỏ hàng và tổng giá tiền
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Trả về lỗi nếu không tìm thấy giỏ hàng
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // API xóa sản phẩm khỏi giỏ hàng
    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<String> removeCartItem(@PathVariable Integer cartItemId) {
        try {
            // Gọi service để xóa sản phẩm khỏi giỏ hàng
            cartService.removeCartItem(cartItemId);
            return ResponseEntity.ok("Sản phẩm đã được xóa khỏi giỏ hàng.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

    // API cập nhật sản phẩm trong giỏ hàng
    @PutMapping("/update/{cartItemId}")
    public ResponseEntity<String> updateCartItem(
            @PathVariable Integer cartItemId,
            @RequestBody CartItem updatedCartItem,
            @RequestParam Integer usersId) {
        try {
            // Gọi service để cập nhật sản phẩm trong giỏ hàng
            cartService.updateCartItem(usersId, cartItemId, updatedCartItem);
            return ResponseEntity.ok("Sản phẩm trong giỏ hàng đã được cập nhật.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Có lỗi xảy ra: " + e.getMessage());
        }
    }

}
