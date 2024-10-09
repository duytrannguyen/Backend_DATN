package com.poly.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.poly.model.CartItem;
import com.poly.model.Product;
import com.poly.model.ShoppingCart;
import com.poly.model.User;
import com.poly.repository.CartItemRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ShoppingCartRepository;
import com.poly.repository.UserRepository;
import com.poly.service.SessionService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ShoppingCartController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	ProductRepository productRepository;

	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	SessionService sessionService;
	@Autowired
	CartItemRepository cartItemRepository;

	@RequestMapping("/home/products/details/cart/{product_id}")
	public String addToCart(Model model, HttpSession session, @PathVariable("product_id") Integer product_id) {
	    // Get the current user
	    User user = (User) session.getAttribute("user");

	    if (user == null) {
	        // User is not logged in, add an attribute to the model to indicate this
	        model.addAttribute("user", null);
	        return "client/Cart"; // Assuming this JSP page handles the case when user is null
	    }

	    // Find the user's existing shopping cart
	    ShoppingCart existingCart = shoppingCartRepository.findByUser(user);
	    if (existingCart == null) {
	        // Create a new shopping cart
	        existingCart = new ShoppingCart();
	        existingCart.setUser(user);
	        existingCart.setTotalPrice(0.0); // Initialize totalPrice to 0
	        existingCart.setFinalPrice(0.0); // Initialize finalPrice to 0
	        existingCart.setQuantity(0); // Initialize quantity to 0
	        shoppingCartRepository.save(existingCart);
	    }

	    // Find the product
	    Product product = productRepository.findById(product_id).orElse(null);
	    if (product == null) {
	        // Handle the case where the product is not found
	        return "error";
	    }
	    
	    double discountedPrice = product.getPrice() - ((product.getPrice() * product.getDiscountPercentage()) / 100);
	    model.addAttribute("discountedPrice", discountedPrice);

	    // Create or update the cart item
	    Sort sort = Sort.by(Direction.DESC, "cartItemId");
	    CartItem existingCartItem = cartItemRepository.findByCartIdAndProductId(existingCart, product_id, sort);
	    if (existingCartItem == null) {
	        // Create a new cart item
	        CartItem cartItem = new CartItem();
	        cartItem.setShoppingCart(existingCart);
	        cartItem.setProductId(product);
	        cartItem.setQuantity(1); // Set the initial quantity
	        cartItem.getProductId().setPrice(product.getPrice()); // Set the price of the product
	        cartItemRepository.save(cartItem);

	        // Update the shopping cart quantity and final price
	        existingCart.setQuantity(existingCart.getQuantity() + 1);
	        existingCart.setTotalPrice(existingCart.getTotalPrice() + product.getPrice());
	        existingCart.setFinalPrice(existingCart.getTotalPrice());
	    } else {
	        // Update the existing cart item
	        existingCartItem.setQuantity(existingCartItem.getQuantity() + 1);
	        cartItemRepository.save(existingCartItem);
	        // Update the shopping cart quantity and final price
	        existingCart.setQuantity(existingCart.getQuantity() + 1);
	        existingCart.setTotalPrice(existingCart.getTotalPrice() + product.getPrice());
	        existingCart.setFinalPrice(existingCart.getTotalPrice());
	    }
	    shoppingCartRepository.save(existingCart);
	    // Add the cart items and the shopping cart to the model
	    List<CartItem> cartItems = cartItemRepository.findByShoppingCart(existingCart, sort);
	    model.addAttribute("cartItems", cartItems);
	    model.addAttribute("shoppingCart", existingCart);

	    return "client/Cart";
	}


	@GetMapping("/home/products/details/cart")
	public String showCart(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		Sort sort = Sort.by(Direction.DESC, "cartItemId");
		ShoppingCart existingCart = shoppingCartRepository.findByUser(user);
		List<CartItem> cartItems = cartItemRepository.findByShoppingCart(existingCart, sort);
		model.addAttribute("cartItems", cartItems);
		return "client/Cart";
	}
	
	@PostMapping("/cart/update")
	public String updateCart(Model model, @RequestParam("cartItemId") Integer cartItemId,
	        @RequestParam("quantity") int newQuantity, HttpSession session) {
	    // Get the current user
//	    Integer userId = 2;
//	    User user = userRepository.findById(userId).get();
	    User user = (User) session.getAttribute("user");
	    
	    // Find the user's existing shopping cart
	    ShoppingCart existingCart = shoppingCartRepository.findByUser(user);
	    if (existingCart == null) {
	        // Handle the case where the user does not have a shopping cart
	        return "client/Cart";
	    }

	    Sort sort = Sort.by(Direction.DESC, "cartItemId");
	    // Find the cart item to update
	    CartItem cartItem = cartItemRepository.findById(cartItemId).get();
	    Product product = cartItem.getProductId(); // Get the product from cart item
	    int oldQuantity = cartItem.getQuantity();
	    cartItem.setQuantity(newQuantity);
	    cartItemRepository.save(cartItem);
	    
	    // Calculate discounted price
	    double originalPrice = product.getPrice();
	    double discountPercentage = product.getDiscountPercentage();
	    double discountedPrice = originalPrice - ((originalPrice * discountPercentage) / 100);
	    model.addAttribute("discountedPrice", discountedPrice);

	    // Update the shopping cart quantity and total price
	    existingCart.setQuantity(existingCart.getQuantity() - oldQuantity + newQuantity);
	    existingCart.setTotalPrice(existingCart.getTotalPrice() - (oldQuantity * originalPrice) 
	            + (newQuantity * originalPrice));
	    existingCart.setFinalPrice(existingCart.getTotalPrice());
	    shoppingCartRepository.save(existingCart);

	    // Refresh the cart items and shopping cart in the model
	    List<CartItem> cartItems = cartItemRepository.findByShoppingCart(existingCart, sort);
	    model.addAttribute("cartItems", cartItems);
	    model.addAttribute("shoppingCart", existingCart);

	    return "client/Cart";
	}


	@GetMapping("/cart/remove/{cartItemId}")
	public String removeCartItem(Model model, @PathVariable("cartItemId") Integer cartItemId, HttpSession session) {
		// Get the current user
//		Integer userId = 2;
//		User user = userRepository.findById(userId).get();
		User user = (User) session.getAttribute("user");
		// Find the user's existing shopping cart
		ShoppingCart existingCart = shoppingCartRepository.findByUser(user);
		if (existingCart == null) {
			// Handle the case where the user does not have a shopping cart
			return "client/Cart";
		}

		Sort sort = Sort.by(Direction.DESC, "cartItemId");

		// Find the cart item to remove
		CartItem cartItem = cartItemRepository.findById(cartItemId).get();

		// Remove the cart item from the shopping cart
		existingCart.setQuantity(existingCart.getQuantity() - cartItem.getQuantity());
		existingCart.setTotalPrice(
				existingCart.getTotalPrice() - (cartItem.getQuantity() * cartItem.getProductId().getPrice()));
		existingCart.setFinalPrice(existingCart.getTotalPrice());
		shoppingCartRepository.save(existingCart);

		// Delete the cart item from the database
		cartItemRepository.deleteById(cartItemId);

		// Refresh the cart items and shopping cart in the model
		List<CartItem> cartItems = cartItemRepository.findByShoppingCart(existingCart, sort);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("shoppingCart", existingCart);

		return "client/Cart";
	}
}
