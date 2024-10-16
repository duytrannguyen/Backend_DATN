package com.poly.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.CartItem;
import com.poly.Model.Product;
import com.poly.Model.User;
import com.poly.Reponsitory.CartItemRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.dto.CartDTO;
import com.poly.dto.ProductDTO;

@Service
public class CartService {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Phương thức lấy giỏ hàng của người dùng và tính tổng giá tiền
    public Map<String, Object> viewCartByUserId(Integer userId) {
        // Tìm kiếm người dùng dựa vào userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại."));
        // Lấy danh sách CartItem của người dùng
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        // Chuyển đổi danh sách CartItem sang danh sách CartDTO
        List<CartDTO> cartDTOs = cartItems.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        // Tính tổng giá tiền
        double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        // Tạo một Map để trả về giỏ hàng và tổng giá
        Map<String, Object> response = new HashMap<>();
        response.put("cartItems", cartDTOs);
        response.put("totalPrice", totalPrice);

        return response;
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addProductToCart(Integer userId, Integer productId, Integer quantity) {
        // Tìm sản phẩm theo ID
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        // Lấy người dùng theo ID
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));
        // Lấy danh sách các mục giỏ hàng của người dùng
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
        Optional<CartItem> existingCartItem = cartItems.stream()
                .filter(cartItem -> cartItem.getProduct().getProductId().equals(productId))
                .findFirst();
        // Xử lý sản phẩm đã có hoặc tạo mới
        if (existingCartItem.isPresent()) {
            // Cập nhật số lượng
            CartItem item = existingCartItem.get();
            item.setQuantity(item.getQuantity() + quantity); // Tăng số lượng theo tham số quantity
            cartItemRepository.save(item); // Lưu thay đổi
        } else {
            // Tạo một mục giỏ hàng mới cho sản phẩm
            CartItem newCartItem = new CartItem();
            newCartItem.setQuantity(quantity); // Số lượng từ tham số quantity
            newCartItem.setProduct(product);
            newCartItem.setUser(user); // Thiết lập người dùng
            cartItemRepository.save(newCartItem); // Lưu mục giỏ hàng mới
        }
    }

    //
    // // // Phương thức lấy giỏ hàng theo userId
    // public List<CartItem> getCartByUserId(User userId) {
    // List<CartItem> cartItems = cartItemRepository.findByUser(userId);
    // if (cartItems.isEmpty()) {
    // throw new RuntimeException("Giỏ hàng không tồn tại cho người dùng này");
    // }
    // return cartItems;
    // }
    //
    // Xóa mục giỏ hàng
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
    //

    // Phương thức cập nhật sản phẩm trong giỏ hàng
    public void updateCartItem(Integer cartItemId, CartItem updatedCartItem) {
        CartItem existingCartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Mục giỏ hàng không tồn tại."));
        // Cập nhật số lượng sản phẩm
        if (updatedCartItem.getQuantity() != null) {
            existingCartItem.setQuantity(updatedCartItem.getQuantity());
        }
        // Nếu cần, bạn có thể cập nhật thêm thông tin khác ở đây
        cartItemRepository.save(existingCartItem);
    }

    // Phương thức để tính toán tổng số lượng và tổng giá tiền cho giỏ hàng
    public Map<String, Double> updateCartTotals(User userId) {
        List<CartItem> cartItems = cartItemRepository.findByUser(userId);
        // Tính tổng số lượng các mục giỏ hàng
        int totalQuantity = cartItems.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
        // Tính tổng giá tiền các mục giỏ hàng
        double totalPrice = cartItems.stream()
                .mapToDouble(cartItem -> cartItem.getProduct().getPrice() * cartItem.getQuantity())
                .sum();
        // Trả về tổng số lượng và tổng giá
        Map<String, Double> totals = new HashMap<>();
        totals.put("totalQuantity", (double) totalQuantity);
        totals.put("totalPrice", totalPrice);
        return totals;
    }

    // GET THÔNG TIN
    private CartDTO convertToDTO(CartItem cartItem) {
        // Khởi tạo CartDTO
        CartDTO dto = new CartDTO();
        // Thiết lập thuộc tính từ CartItem
        dto.setCartItemId(cartItem.getCartItemId());
        dto.setQuantity(cartItem.getQuantity());
        dto.setUser(cartItem.getUser().getUsersId()); // Giả sử bạn có phương thức getId() trong lớp User
        // Thiết lập sản phẩm
        Product product = cartItem.getProduct();
        if (product != null) {
            ProductDTO productDTO = new ProductDTO();
            productDTO.setProductId(product.getProductId()); // Giả sử bạn có phương thức getId() trong lớp Product
            productDTO.setProductName(product.getProductName());
            productDTO.setPrice(product.getPrice());
            productDTO.setSize(product.getSize());
            productDTO.setMaterial(product.getMaterial());
            productDTO.setDescription(product.getDescription());
            productDTO.setPlaceProduction(product.getPlaceProduction());
            productDTO.setPostingDate(product.getPostingDate());
            productDTO.setQuantity(product.getQuantity());
            productDTO.setStatusName(product.getStatus());
            productDTO.setCategoryName(product.getCategory().getCategoryName());
            productDTO.setImageUrl(product.getImageId().getImageName());
            productDTO.setStatusName(product.getStatus());
            productDTO.setSellerName(product.getSeller().getShopName());
            // Thêm ProductDTO vào danh sách
            List<ProductDTO> productItems = new ArrayList<>();
            productItems.add(productDTO);
            dto.setProducts(productItems); // Thiết lập danh sách sản phẩm vào CartDTO
        } else {
            // Nếu không có sản phẩm, bạn có thể để danh sách sản phẩm rỗng
            dto.setProducts(new ArrayList<>());
        }

        return dto;
    }

    // Chuyển từ DTO sang Entity
    // private CartItem convertToEntity(CartDTO dto) {
    // CartItem cartItem = new CartItem();
    // // Thiết lập thuộc tính cho CartItem từ CartDTO
    // cartItem.setCartItemId(dto.getCartItemId());
    // cartItem.setQuantity(dto.getQuantity());
    // // Thiết lập User
    // User user = new User();
    // user.setUsersId(dto.getUser()); // Giả sử bạn có phương thức setId() trong
    // lớp User
    // cartItem.setUser(user);
    // // Thiết lập Product
    // Product product = new Product();
    // cartItem.setProduct(product);
    // return cartItem;
    // }

}
