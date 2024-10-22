package com.poly.Model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Invoices") // Gắn với bảng 'Invoices' trong cơ sở dữ liệu
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id") // Tên cột trong cơ sở dữ liệu
    private Integer invoiceId; // ID hóa đơn

    @Column(nullable = false)
    private Double totalAmount; // Tổng số tiền

    @Column(name = "feeship")
    private Double feeShip;

    @Column(name = "fulladdress")
    private String fullAddress;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày thanh toán
    private Date paymentDate; // Ngày thanh toán

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false) // Ánh xạ đến bảng Users
    private User user; // Người dùng

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false) // Ánh xạ đến bảng OrderStatus
    private OrderStatus status; // Trạng thái đơn hàng

    @ManyToOne
    @JoinColumn(name = "payment_method_id", nullable = false) // Ánh xạ đến bảng PaymentMethods
    private PaymentMethod paymentMethod; // Phương thức thanh toán

    @ManyToOne
    @JoinColumn(name = "discount_detail_id", nullable = true) // Ánh xạ đến bảng DiscountDetails
    private DiscountDetail discountDetail; // Chi tiết giảm giá

    @OneToMany(mappedBy = "invoice") // Ánh xạ đến bảng InvoiceItem
    private List<InvoiceItem> invoiceItems; // Danh sách mục hóa đơn
}
