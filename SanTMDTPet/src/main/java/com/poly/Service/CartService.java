package com.poly.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.CartItem;
import com.poly.Model.Product;
import com.poly.Model.ShoppingCart;
import com.poly.Model.User;
import com.poly.Reponsitory.CartItemRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Reponsitory.ShoppingCartRepository;
import com.poly.Reponsitory.UserRepository;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private UserRepository userRepository;

    public void addProductToCart(Integer userId, Integer productId, Integer quantity) {
        // Lấy giỏ hàng của người dùng nếu đã tồn tại, nếu không thì tạo mới
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_UsersId(userId)
                .orElseGet(() -> createNewCartForUser(userId));

        // Tìm sản phẩm theo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        Optional<CartItem> existingCartItem = shoppingCart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProductId().equals(product))
                .findFirst();

        if (existingCartItem.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            CartItem cartItem = existingCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            // Nếu sản phẩm chưa có trong giỏ hàng, tạo mới một mục giỏ hàng
            CartItem newCartItem = new CartItem();
            newCartItem.setProductId(product);
            newCartItem.setQuantity(quantity);
            newCartItem.setShoppingCart(shoppingCart);
            shoppingCart.getCartItems().add(newCartItem);
        }

        // Tính tổng số lượng các mục giỏ hàng
        int totalQuantity = shoppingCart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        // Tính tổng giá tiền các mục giỏ hàng
        double totalPrice = shoppingCart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProductId().getPrice() * cartItem.getQuantity())
                .sum();

        // Cập nhật tổng số lượng và tổng giá tiền vào giỏ hàng
        shoppingCart.setQuantity(totalQuantity);
        shoppingCart.setFinalPrice(totalPrice);
        shoppingCart.setTotalPrice(totalPrice);

        // Lưu giỏ hàng sau khi thêm hoặc cập nhật sản phẩm
        shoppingCartRepository.save(shoppingCart);
    }

    // Phương thức tạo giỏ hàng mới cho người dùng
    private ShoppingCart createNewCartForUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        ShoppingCart newCart = new ShoppingCart();
        newCart.setUser(user);
        return shoppingCartRepository.save(newCart);
    }

    // Phương thức lấy giỏ hàng theo userId
    public ShoppingCart getCartByUserId(Integer userId) {
        return shoppingCartRepository.findByUser_UsersId(userId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));
    }

    // Tính tổng giá tiền
    public double calculateTotalPrice(Integer userId) {
        ShoppingCart shoppingCart = getCartByUserId(userId);

        return shoppingCart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProductId().getPrice() * cartItem.getQuantity())
                .sum();
    }

    // xoá
    public void removeCartItem(Integer cartItemId) {
        // Tìm kiếm mục giỏ hàng theo ID
        Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
        if (cartItemOptional.isPresent()) {
            // Xóa mục giỏ hàng
            cartItemRepository.delete(cartItemOptional.get());
        } else {
            throw new RuntimeException("Mục giỏ hàng không tồn tại");
        }
    }

    // cập nhật giỏ hàng
    public void updateCartItem(Integer userId, Integer cartItemId, CartItem updatedCartItem) {
        // Lấy giỏ hàng của người dùng
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_UsersId(userId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại cho người dùng này"));
        // Tìm kiếm mục giỏ hàng theo ID
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Mục giỏ hàng không tồn tại"));
        // Kiểm tra xem mục giỏ hàng có thuộc về giỏ hàng của người dùng hay không
        if (!existingCartItem.getShoppingCart().equals(shoppingCart)) {
            throw new RuntimeException("Mục giỏ hàng không thuộc về giỏ hàng của người dùng này");
        }
        // Cập nhật số lượng
        existingCartItem.setQuantity(updatedCartItem.getQuantity());
        // Lưu lại mục giỏ hàng đã cập nhật
        cartItemRepository.save(existingCartItem);
        // Cập nhật giỏ hàng
        updateShoppingCartTotals(shoppingCart);
    }

    // Phương thức để cập nhật tổng số lượng và tổng giá tiền cho giỏ hàng
    private void updateShoppingCartTotals(ShoppingCart shoppingCart) {
        // Tính tổng số lượng các mục giỏ hàng
        int totalQuantity = shoppingCart.getCartItems().stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        // Tính tổng giá tiền các mục giỏ hàng
        double totalPrice = shoppingCart.getCartItems().stream()
                .mapToDouble(cartItem -> cartItem.getProductId().getPrice() * cartItem.getQuantity())
                .sum();
        // Cập nhật tổng số lượng và tổng giá tiền vào giỏ hàng
        shoppingCart.setQuantity(totalQuantity);
        shoppingCart.setFinalPrice(totalPrice);
        shoppingCart.setTotalPrice(totalPrice);
        // Lưu giỏ hàng sau khi cập nhật
        shoppingCartRepository.save(shoppingCart);
    }

}
