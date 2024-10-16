package com.poly.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.poly.Exception.ResourceNotFoundException;
import com.poly.Model.CartItem;
import com.poly.Model.DiscountDetail;
import com.poly.Model.Invoice;
import com.poly.Model.InvoiceItem;
import com.poly.Model.OrderStatus;
import com.poly.Model.PaymentMethod;
import com.poly.Model.Product;
import com.poly.Model.User;
import com.poly.Reponsitory.CartItemRepository;
import com.poly.Reponsitory.DiscountDetailRepository;
import com.poly.Reponsitory.InvoiceItemRepository;
import com.poly.Reponsitory.InvoiceRepository;
import com.poly.Reponsitory.OrderStatusRepository;
import com.poly.Reponsitory.PaymentMethodRepository;
import com.poly.Reponsitory.ProductRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.dto.InvoiceDTO;
import com.poly.dto.InvoiceItemDTO;
import com.poly.dto.ProductDTO;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;
    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository; // Để lấy thông tin người dùng từ ID

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    // Lấy tất cả hóa đơn
    public List<InvoiceDTO> getAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Trạng thái hoá đơn của seller ( HIỂN THỊ QUẢN LÝ DANH SÁCH HOÁ ĐƠN CỦA
    // SELLER)
    public List<InvoiceDTO> getInvoicesBySellerIdAndStatusName(int sellerId, List<String> StatusName) {
        List<Invoice> invoices = invoiceRepository.findBySellerIdAndStatusNames(sellerId, StatusName);
        // Ánh xạ từ Invoice sang InvoiceDTO
        return invoices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Lấy một hóa đơn theo ID
    public Optional<InvoiceDTO> getInvoiceById(int id) {
        return invoiceRepository.findById(id).map(this::convertToDTO);
    }

    // // Thêm mới hóa đơn
    public InvoiceDTO createInvoiceFromCart(Integer userId, InvoiceDTO invoiceDTO) {
        // Lấy người dùng từ userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tìm thấy với id " + userId));
        // Lấy danh sách sản phẩm trong giỏ hàng của người dùng
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng rỗng.");
        }
        // Tạo trạng thái đơn hàng mới
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setStatusName("ĐÃ ĐẶT HÀNG");
        orderStatus.setEstimated_delivery_date(new Date()); // Ngày hiện tại
        // Lưu trạng thái đơn hàng vào cơ sở dữ liệu
        OrderStatus savedOrderStatus = orderStatusRepository.save(orderStatus);
        // Tạo một đối tượng Invoice mới từ DTO
        Invoice invoice = convertToEntity(invoiceDTO);
        invoice.setPaymentDate(new Date()); // Ngày hiện tại
        invoice.setStatus(savedOrderStatus); // Thiết lập trạng thái cho hóa đơn
        // Lưu hóa đơn vào cơ sở dữ liệu trước
        Invoice savedInvoice = invoiceRepository.save(invoice); // Lưu hóa đơn và nhận lại đối tượng đã lưu
        double totalAmount = 0.0;
        // Lặp qua các sản phẩm trong giỏ hàng
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            double price = product.getPrice();
            int quantity = cartItem.getQuantity();
            // Tạo một InvoiceItem mới
            InvoiceItem invoiceItem = new InvoiceItem();
            invoiceItem.setQuantity(quantity);
            invoiceItem.setPrice(price);
            invoiceItem.setProduct(product);
            invoiceItem.setInvoice(savedInvoice); // Gán hóa đơn đã lưu vào InvoiceItem
            totalAmount += price * quantity;
            // Lưu mục hóa đơn vào cơ sở dữ liệu
            invoiceItemRepository.save(invoiceItem);
        }
        // Đặt tổng giá trị cho hóa đơn
        savedInvoice.setTotalAmount(totalAmount);

        // Cập nhật hóa đơn đã lưu với tổng giá trị
        invoiceRepository.save(savedInvoice); // Cập nhật lại hóa đơn nếu cần
        return convertToDTO(savedInvoice);
    }

    public OrderStatus updateOrderStatus(Integer id, OrderStatus orderStatus) {
        if (orderStatusRepository.existsById(id)) {
            // Lấy trạng thái hiện tại
            OrderStatus currentStatus = orderStatusRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Order status not found with id " + id));
            String currentStatusName = currentStatus.getStatusName(); // Tên trạng thái hiện tại
            // Kiểm tra nếu trạng thái hiện tại là "ĐANG GIAO HÀNG"
            // if ("ĐANG GIAO HÀNG".equals(currentStatusName)) {
            // throw new RuntimeException("ĐƠN HÀNG ĐANG ĐƯỢC GIAO KHÔNG THỂ HUỶ");
            // }
            // Cập nhật trạng thái dựa trên trạng thái hiện tại
            if ("ĐÃ ĐẶT HÀNG".equals(currentStatusName)) {
                currentStatus.setStatusName("ĐANG XỬ LÝ");
            } else if ("ĐANG XỬ LÝ".equals(currentStatusName)) {
                currentStatus.setStatusName("ĐANG GIAO HÀNG");
            } else if ("ĐANG GIAO HÀNG".equals(currentStatusName)) {
                currentStatus.setStatusName("ĐÃ GIAO HÀNG");
            } else if ("ĐÃ GIAO HÀNG".equals(currentStatusName)) {
                currentStatus.setStatusName("HOÀN THÀNH");
            }
            currentStatus.setEstimated_delivery_date(new Date()); // Cập nhật ngày ước lượng giao hàng
            // Lưu trạng thái đã cập nhật
            return orderStatusRepository.save(currentStatus);
        } else {
            throw new RuntimeException("Order status not found with id " + id);
        }

    }

    // Lọc hoá đơn theo usersid và theo các trạng thái hoá đơn
    public List<InvoiceDTO> getInvoicesByUserIdAndStatusName(int userId, List<String> StatusName) {
        // Lấy danh sách hóa đơn theo userId
        List<Invoice> invoices = invoiceRepository.findByUser_UsersId(userId);

        // Lọc hóa đơn theo danh sách trạng thái và ánh xạ sang DTO
        return invoices.stream().filter(invoice -> StatusName.contains(invoice.getStatus().getStatusName()))
                .map(this::convertToDTO) // Ánh xạ từ Invoice sang InvoiceDTO
                .collect(Collectors.toList());
    }

    // Xóa hóa đơn theo ID
    public void deleteInvoice(int id) {
        invoiceRepository.deleteById(id);
    }

    // CHO CÁC HÀM GET THÔNG TIN
    private InvoiceDTO convertToDTO(Invoice invoice) {
        InvoiceDTO dto = new InvoiceDTO();
        // Thiết lập các thuộc tính cơ bản
        dto.setInvoiceId(invoice.getInvoiceId());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setPaymentDate(invoice.getPaymentDate());
        dto.setUsersId(invoice.getUser().getUsersId());
        dto.setPaymentDate(new Date()); // Ngày hiện tại
        // Kiểm tra feeShip có phải null không trước khi gọi doubleValue()
        dto.setFeeShip(invoice.getFeeShip() != null ? invoice.getFeeShip().doubleValue() : 0.0);
        dto.setFullAddress(invoice.getFullAddress());
        dto.setOrderStatusName(invoice.getStatus().getStatusName());
        // Thiết lập DiscountCodeId
        dto.setDiscountCodeId(
                invoice.getDiscountDetail() != null ? invoice.getDiscountDetail().getDiscountDetailId() : 0); // Giá trị
                                                                                                              // mặc
                                                                                                              // định
                                                                                                              // nếu
                                                                                                              // không
                                                                                                              // có
                                                                                                              // discount

        // Thiết lập OrderStatusId
        dto.setOrderStatusId(invoice.getStatus() != null ? invoice.getStatus().getStatusId() : 0); // Giá trị mặc định
                                                                                                   // cho trường hợp
                                                                                                   // không có trạng
                                                                                                   // thái

        // Thiết lập PaymentMethodId
        dto.setPaymentMethodId(
                invoice.getPaymentMethod() != null ? invoice.getPaymentMethod().getPaymentMethodId() : 0); // Giá trị
                                                                                                           // mặc định
                                                                                                           // nếu không
                                                                                                           // có phương
                                                                                                           // thức
                                                                                                           // thanh
                                                                                                           // toán

        // Thiết lập danh sách các InvoiceItemDTO
        List<InvoiceItemDTO> invoiceItems = new ArrayList<>();
        if (invoice.getInvoiceItems() != null) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                InvoiceItemDTO itemDTO = new InvoiceItemDTO();
                itemDTO.setInvoiceItemId(item.getInvoiceItemId());
                itemDTO.setPrice(item.getPrice());
                itemDTO.setQuantity(item.getQuantity());

                // Thiết lập sản phẩm từ InvoiceItem
                Product product = item.getProduct(); // Lấy sản phẩm từ InvoiceItem
                List<ProductDTO> productItems = new ArrayList<>(); // Khởi tạo danh sách sản phẩm

                if (product != null) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setProductId(product.getProductId());
                    productDTO.setProductName(product.getProductName());
                    productDTO.setPrice(product.getPrice());
                    productDTO.setSize(product.getSize());
                    productDTO.setMaterial(product.getMaterial());
                    productDTO.setDescription(product.getDescription());
                    productDTO.setPlaceProduction(product.getPlaceProduction());
                    productDTO.setPostingDate(product.getPostingDate());
                    productDTO.setQuantity(product.getQuantity());
                    productDTO.setStatusName(product.getStatus().getStatusName());
                    productDTO.setCategoryName(
                            product.getCategory() != null ? product.getCategory().getCategoryName() : null);
                    productDTO.setImageUrl(product.getImageId() != null ? product.getImageId().getImageName() : null);
                    // productDTO.setStatusName(product.getStatus());
                    productDTO.setSellerName(product.getSeller() != null ? product.getSeller().getShopName() : null);

                    // Thêm ProductDTO vào danh sách
                    productItems.add(productDTO);
                }

                itemDTO.setProducts(productItems); // Thiết lập danh sách sản phẩm vào InvoiceItemDTO
                invoiceItems.add(itemDTO); // Thêm InvoiceItemDTO vào danh sách
            }
        }

        dto.setInvoiceItems(invoiceItems); // Gán danh sách InvoiceItemDTO vào InvoiceDTO
        return dto;
    }

    // CHO CÁC HÀM THÊM, SỬA
    // Chuyển từ DTO sang Entity
    private Invoice convertToEntity(InvoiceDTO dto) {
        Invoice invoice = new Invoice();
        // Thiết lập các thuộc tính từ DTO
        invoice.setTotalAmount(dto.getTotalAmount());
        invoice.setPaymentDate(dto.getPaymentDate());
        invoice.setFeeShip(dto.getFeeShip());
        invoice.setFullAddress(dto.getFullAddress());
        // Lấy đối tượng User từ repository
        User user = userRepository.findById(dto.getUsersId())
                .orElseThrow(() -> new RuntimeException("User not found with id " + dto.getUsersId()));
        invoice.setUser(user);
        // Gán trạng thái
        if (dto.getOrderStatusId() != null) {
            OrderStatus orderStatus = orderStatusRepository.findById(dto.getOrderStatusId()).orElseThrow(
                    () -> new RuntimeException("Order status not found with id " + dto.getOrderStatusId()));
            invoice.setStatus(orderStatus);
        }
        // Gán phương thức thanh toán
        if (dto.getPaymentMethodId() != null) {
            PaymentMethod paymentMethod = paymentMethodRepository.findById(dto.getPaymentMethodId()).orElseThrow(
                    () -> new RuntimeException("Payment method not found with id " + dto.getPaymentMethodId()));
            invoice.setPaymentMethod(paymentMethod);
        }
        // Gán chi tiết giảm giá
        if (dto.getDiscountCodeId() != null) {
            DiscountDetail discountDetail = discountDetailRepository.findById(dto.getDiscountCodeId())
                    .orElseThrow(() -> new RuntimeException("Voucher not found with id " + dto.getDiscountCodeId()));
            invoice.setDiscountDetail(discountDetail);
        }
        return invoice;
    }

    // TRANG BÁO CÁ0
    // Lấy tổng số đơn hàng của seller dựa trên sellerId
    public Long getTotalOrdersBySeller(int sellerId) {
        return invoiceRepository.countBySeller_sellerId(sellerId);
    }

    // Lấy tổng số tiền từ các đơn hàng của seller dựa trên sellerId
    public Double getTotalAmountBySeller(int sellerId) {
        return invoiceRepository.findTotalAmountBySeller_sellerId(sellerId);
    }
}
