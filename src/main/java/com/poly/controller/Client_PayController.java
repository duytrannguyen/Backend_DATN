package com.poly.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.poly.config.PayOSConfig;
import com.poly.config.VNPAYConfig;
import com.poly.model.Address;
import com.poly.model.CartItem;
import com.poly.model.CreatePaymentLinkRequestBody;
import com.poly.model.Discount;
import com.poly.model.Invoice;
import com.poly.model.InvoiceItem;
import com.poly.model.OrderStatus;
import com.poly.model.PaymentMethod;
import com.poly.model.Product;
import com.poly.model.Shipping;
import com.poly.model.ShoppingCart;
import com.poly.model.User;
import com.poly.repository.CartItemRepository;
import com.poly.repository.InvoiceItemRepository;
import com.poly.repository.InvoiceRepository;
import com.poly.repository.ProductRepository;
import com.poly.repository.ShoppingCartRepository;
import com.poly.repository.UserRepository;
import com.poly.service.CartService;
import com.poly.service.DiscountService;
//import com.poly.service.PayOSService;
import com.poly.service.SessionService;
import com.poly.util.VNPayUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Client_PayController {
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	ShoppingCartRepository shoppingCartRepository;
	@Autowired
	InvoiceItemRepository invoiceItemRepository;
	@Autowired
	InvoiceRepository invoiceRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	SessionService sessionService;
	@Autowired
	JavaMailSender sender;
	@Autowired
	CartService cartService; // Dịch vụ giỏ hàng
	@Autowired
	DiscountService discountService;
	@Autowired
	VNPAYConfig vnPayConfig;
	@Autowired
	PayOSConfig payOSConfig;
	@Autowired
	PayOS payOS;

	@RequestMapping("/products/details/cart/pay")
	public String pay(@RequestParam("cartItemIds") String cartItemIds, Model model) {
		// Lấy thông tin người dùng từ session
		User user = (User) sessionService.get("user");
		if (user == null) {
			return "redirect:/home/login"; // Chuyển hướng đến trang đăng nhập nếu không có người dùng trong session
		}
		// Lấy giỏ hàng hiện tại của người dùng
		ShoppingCart shoppingCart = shoppingCartRepository.findCurrentCartByUser(user);
		if (shoppingCart == null) {
			return "giohangtrong"; // Chuyển hướng đến trang thông báo giỏ hàng trống nếu không tìm thấy giỏ hàng
		}
		// Tính tổng giá trị giỏ hàng dựa trên cartItemIds
		double total = 0;
		List<CartItem> cartItems = new ArrayList<>();
		String[] idStrings = cartItemIds.split(",");
		for (String id : idStrings) {
			try {
				int cartItemId = Integer.parseInt(id);
				Optional<CartItem> cartItemOptional = cartItemRepository.findById(cartItemId);
				if (cartItemOptional.isPresent()) {
					CartItem cartItem = cartItemOptional.get();
					total += cartItem.getQuantity() * cartItem.getProductId().getPrice();
					cartItems.add(cartItem);
				} else {

					return "errorpage";
				}
			} catch (NumberFormatException e) {
				// Handle invalid cart item id (should not occur if input is validated properly)
				return "errorpage";
			}
		}
		// User user = userRepository.findById(userId);
		Hibernate.initialize(user.getAddresses());
		Address defaultAddress = user.getAddresses().stream().filter(Address::isStatus).findFirst().orElse(null);
		model.addAttribute("address", defaultAddress);
		// Lưu thông tin vào model
		model.addAttribute("total", total);
		// model.addAttribute("address", defaultAddress);
		model.addAttribute("user", user);
		model.addAttribute("cartItems", cartItems);
		// Lưu cart_id vào session
		sessionService.set("cartId", shoppingCart.getCartId());
		// Lưu danh sách sản phẩm vào session
		sessionService.set("cartItems", cartItems);
		return "client/Pay"; // Trả về view Pay để người dùng thanh toán
	}

	@GetMapping("/products/details/cart/paynow")
	public String paynow(@RequestParam("productId") Integer productId, Model model, HttpSession session) {
		// Lấy thông tin người dùng hiện tại từ session
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/home/login"; // Chuyển hướng đến trang đăng nhập nếu người dùng chưa đăng nhập
		}
////		// Tìm giỏ hàng hiện tại của người dùng
		ShoppingCart shoppingCart = shoppingCartRepository.findByUser(user);
		if (shoppingCart == null) {
			// Nếu không tìm thấy giỏ hàng, tạo mới một giỏ hàng
			shoppingCart = new ShoppingCart();
			shoppingCart.setUser(user);
			shoppingCartRepository.save(shoppingCart);
		}
		// Tìm sản phẩm dựa trên productId
		Product product = productRepository.findById(productId).orElse(null);
		if (product == null) {
			// Xử lý trường hợp không tìm thấy sản phẩm
			return "error"; // Có thể chuyển hướng đến trang lỗi
		}
		// Tạo một CartItem mới cho sản phẩm
		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(shoppingCart);
		cartItem.setProductId(product);
		cartItem.setQuantity(1); // Thiết lập số lượng mặc định
		cartItemRepository.save(cartItem);
		// Xóa các sản phẩm đã chọn từ giỏ hàng khi người dùng quay lại
		List<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);
		cartItemRepository.deleteAll(cartItems);
		// Tính toán tổng tiền
		double total = 0;
		for (CartItem item : cartItems) {
			total += item.getProductId().getPrice() * item.getQuantity();
		}
		// Lưu cartId và cartItems vào session
		session.setAttribute("cartId", shoppingCart.getCartId());
		session.setAttribute("cartItems", cartItems);
		// Thêm các thuộc tính cần thiết vào model cho trang thanh toán
		model.addAttribute("user", user);
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("total", total);
		Address defaultAddress = user.getAddresses().stream().filter(Address::isStatus).findFirst().orElse(null);
		if (defaultAddress != null) {
			System.out.println("Default Address: " + defaultAddress);
		} else {
			System.out.println("Default Address is null");
		}
		model.addAttribute("address", defaultAddress);
		// Tạo đối tượng invoice và thêm vào model
		Invoice invoice = new Invoice();
		model.addAttribute("invoice", invoice);
		// Chuyển hướng đến trang thanh toán
		return "client/Pay";
	}

	@PostMapping("/products/details/cart/pay")
	public String pay(@ModelAttribute("invoice") Invoice invoice, @RequestParam("status") Integer status,
			@RequestParam("productName") String productName, @RequestParam("payment_method_id") Integer payment_method_id,
			@RequestParam("shipping_id") Integer shippingId, @RequestParam(name = "totalAmount") double totalAmount,
			@RequestParam(name = "quantity") int quantity, @RequestParam(name = "price") double price,
			HttpSession session, HttpServletRequest request, Model model, HttpServletResponse httpServletResponse)
			throws Exception {
		// Lấy thông tin người dùng từ session
		User user = (User) session.getAttribute("user");
		if (user == null) {
			return "redirect:/error"; // Chuyển hướng đến trang lỗi nếu không tìm thấy người dùng
		}
		// Tạo và thiết lập các đối tượng liên quan
		Shipping shipping = new Shipping();
		shipping.setShipping_id(shippingId); // Đảm bảo rằng shipping_id là giá trị hợp lệ
		OrderStatus orderStatus = new OrderStatus();
		orderStatus.setStatusId(status);
		PaymentMethod paymentMethod = new PaymentMethod();
		paymentMethod.setPaymentMethodId(payment_method_id);
		// Cập nhật thông tin hóa đơn
		invoice.setStatus(orderStatus);
		invoice.setPaymentMethod(paymentMethod);
		invoice.setShipping(shipping);
		invoice.setPaymentDate(new Date());
		invoice.setUser(user); // Sửa thành user
		invoice.setTotalAmount(totalAmount);
		// Nếu phương thức thanh toán là VNPay, chuyển hướng đến cổng thanh toán VNPay
		if (payment_method_id == 2) {
			// Tạo yêu cầu VNPay
			Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig(request);
			long amount = (long) (totalAmount * 100); // Quy đổi số tiền thành VND
			vnpParamsMap.put("vnp_Amount", String.valueOf(amount));
			vnpParamsMap.put("vnp_TxnRef", VNPayUtil.getRandomNumber(8));
			vnpParamsMap.put("vnp_OrderInfo", "Thanh toan don hang:" + VNPayUtil.getRandomNumber(8));
			vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));
			// Tạo URL yêu cầu thanh toán VNPay
			String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, vnPayConfig.getSecretKey());
			String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
			System.out.println("orderId "+invoice.getInvoiceId());
			System.out.println("URL yêu cầu VNPay: " + paymentUrl);
			// Lưu hóa đơn vào session để sử dụng sau khi thanh toán thành công
			session.setAttribute("invoice", invoice);
			session.setAttribute("cartItems", sessionService.get("cartItems"));
			return "redirect:" + paymentUrl;
		} 
		if (payment_method_id == 3) {
		    // Tạo đối tượng yêu cầu thanh toán
		    final String returnUrl = "http://localhost:8080/success";
		    final String cancelUrl = "http://localhost:8080/cancel";
		    String currentTimeString = String.valueOf(new Date().getTime());
		    long orderCode = Long.parseLong(currentTimeString.substring(currentTimeString.length() - 6));
		    final String description = user.getUsername()+ " " + orderCode;
//		    System.out.println(orderCode);
		    ItemData item = ItemData.builder()
		            .name(productName)
		            .quantity(quantity)
		            .price((int) price)
		            .build();
		    PaymentData paymentData = PaymentData.builder()
		            .orderCode(orderCode)
		            .amount((int) totalAmount)
		            .description(description)
		            .returnUrl(returnUrl)
		            .cancelUrl(cancelUrl)
		            .item(item)
		            .build();
		    
		    try {
		        // Tạo liên kết thanh toán với PayOS
		        CheckoutResponseData responseData = payOS.createPaymentLink(paymentData);
		        String checkoutUrl = responseData.getCheckoutUrl();
		        
		        // Lưu thông tin hóa đơn vào cơ sở dữ liệu và session
		        session.setAttribute("invoice", invoice);
		        session.setAttribute("cartItems", session.getAttribute("cartItems"));
		        // Chuyển hướng đến trang thanh toán
		        return "redirect:" + checkoutUrl;
		    } catch (Exception e) {
		        e.printStackTrace();
		        // Xử lý lỗi
		        return "redirect:/error";
		    }
		}

		// Lưu hóa đơn vào cơ sở dữ liệu cho các phương thức thanh toán khác
		invoiceRepository.saveAndFlush(invoice);
		saveInvoiceItems(session, invoice);
		sessionService.remove("cartItems");
		model.addAttribute("orderId", invoice.getInvoiceId());
		model.addAttribute("totalAmount", invoice.getTotalAmount());
		model.addAttribute("payment_method_id", invoice.getPaymentMethod().getPaymentMethodName());
		model.addAttribute("orderTime", invoice.getPaymentDate());

		return "/client/PaySuccess";
	}
	
	private void saveInvoiceItems(HttpSession session, Invoice invoice) {
		// Chuyển đổi các mục giỏ hàng thành các mục hóa đơn
		List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");
		if (cartItems != null && !cartItems.isEmpty()) {
			for (CartItem cartItem : cartItems) {
				InvoiceItem invoiceItem = new InvoiceItem();
				invoiceItem.setInvoice(invoice);
				Product product = cartItem.getProductId();
				if (product != null) {
					invoiceItem.setProduct(product);
					invoiceItem.setPrice(product.getPrice());
				} else {
					continue; // Bỏ qua và chuyển sang mục tiếp theo
				}
				invoiceItem.setQuantity(cartItem.getQuantity());
				invoiceItemRepository.saveAndFlush(invoiceItem);
				cartItemRepository.delete(cartItem);
			}
		}
	}
//vnpay
	@GetMapping("/response")
	public String handleVNPayResponse(@RequestParam Map<String, String> params, HttpSession session,
			HttpServletRequest request, Model model) {
		// Log all parameters
		params.forEach((key, value) -> System.out.println(key + ": " + value));
		String vnpResponseCode = params.get("vnp_ResponseCode");
		String vnpTxnRef = params.get("vnp_TxnRef");
		String vnpAmount = params.get("vnp_Amount");
		// Validate the signature
		boolean isValidSignature = VNPayUtil.validateSignature(params, vnPayConfig.getSecretKey());
		System.out.println("VNP Response Code: " + vnpResponseCode);
		System.out.println("VNP Txn Ref: " + vnpTxnRef);
		System.out.println("VNP Amount: " + vnpAmount);
		System.out.println("Is Valid Signature: " + isValidSignature);
		if (isValidSignature) {
			if ("00".equals(vnpResponseCode)) {
				// Lấy hóa đơn và các mục giỏ hàng từ session
				Invoice invoice = (Invoice) session.getAttribute("invoice");
				if (invoice != null) {
					invoiceRepository.saveAndFlush(invoice);
					saveInvoiceItems(session, invoice);
					sessionService.remove("cartItems");
					session.removeAttribute("invoice");
				}
				model.addAttribute("orderId", invoice.getInvoiceId());
				model.addAttribute("totalAmount", invoice.getTotalAmount());
				model.addAttribute("payment_method_id", invoice.getPaymentMethod().getPaymentMethodName());
				model.addAttribute("orderTime", invoice.getPaymentDate());
				// Handle successful payment
				return "/client/PaySuccess";
			} else {
				// Handle failed payment
//				model.addAttribute("errorCode", vnpResponseCode);
				model.addAttribute("errorMessage", "Thanh toán không thành công do đã hủy thanh toán!!");
				return "/client/PaymentFailed";
			}
		} else {
			// Handle invalid signature
			model.addAttribute("errorCode", "000");
			model.addAttribute("errorMessage", "Chữ ký không hợp lệ");
			return "/client/PaymentFailed";
		}
	}

	//payos
	@RequestMapping("/success")
	public String handlePaymentSuccess(HttpServletRequest request, HttpServletResponse response,HttpSession session,Model model) {
		// Lấy hóa đơn và các mục giỏ hàng từ session
		Invoice invoice = (Invoice) session.getAttribute("invoice");
		if (invoice != null) {
			invoiceRepository.saveAndFlush(invoice);
			saveInvoiceItems(session, invoice);
			sessionService.remove("cartItems");
			session.removeAttribute("invoice");
		}
		model.addAttribute("orderId", invoice.getInvoiceId());
		model.addAttribute("totalAmount", invoice.getTotalAmount());
		model.addAttribute("paymentMethod", invoice.getPaymentMethod().getPaymentMethodName());
		model.addAttribute("orderTime", invoice.getPaymentDate());
		return "/client/PaySuccess";
	}

	@RequestMapping("/cancel")
	public String handlePaymentCancel(HttpServletRequest request,Model model) {
		model.addAttribute("errorMessage", "Đã hủy thanh toán!!");
	    // Xử lý khi thanh toán bị hủy
	    return "/client/PaymentFailed"; // Trang thông báo hủy
	}

	@PostMapping("/apply-discount")
	public ResponseEntity<Map<String, Object>> applyDiscount(@RequestBody Map<String, String> payload) {
		String discountCode = payload.get("discountCode");
		Map<String, Object> response = new HashMap<>();
		try {
			double discountAmount = discountService.getDiscountAmount(discountCode);
			// Áp dụng giảm giá và tính toán tổng tiền
			double currentTotal = cartService.getCurrentTotal(); // Lấy tổng tiền hiện tại từ giỏ hàng
			double newTotal = currentTotal - discountAmount + 32000; // Tổng tiền sau khi áp dụng giảm giá và cộng phí
																		// vận chuyển
			cartService.applyDiscount(discountAmount); // Áp dụng giảm giá
			response.put("success", true);
			response.put("discountAmount", discountAmount);
			response.put("total", newTotal); // Trả về tổng tiền sau khi áp dụng giảm giá
		} catch (IllegalArgumentException e) {
			response.put("success", false);
			response.put("message", e.getMessage());
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Có lỗi xảy ra, vui lòng thử lại sau.");
		}
		return ResponseEntity.ok(response);
	}

	@PostMapping("/remove-discount")
	public ResponseEntity<Map<String, Object>> removeDiscount() {
		Map<String, Object> response = new HashMap<>();
		try {
			double currentTotal = cartService.getCurrentTotal();
			double shippingCost = 32000; // Phí vận chuyển

			// Cập nhật tổng tiền sau khi gỡ bỏ giảm giá
			double newTotal = currentTotal + shippingCost;
			cartService.removeDiscount(); // Xóa giảm giá trong dịch vụ giỏ hàng

			response.put("success", true);
			response.put("newTotal", newTotal); // Trả về tổng tiền sau khi gỡ bỏ giảm giá
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Có lỗi xảy ra, vui lòng thử lại sau.");
		}
		return ResponseEntity.ok(response);
	}

	private double calculateTotalWithDiscount(double total, double discountAmount) {
		return total - discountAmount;
	}

}
