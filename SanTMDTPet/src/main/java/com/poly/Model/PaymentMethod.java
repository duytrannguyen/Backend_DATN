package com.poly.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "PaymentMethods") // Gắn với bảng 'PaymentMethods' trong cơ sở dữ liệu
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id") // Tên cột trong cơ sở dữ liệu
    private Integer paymentMethodId; // ID phương thức thanh toán

    @Column(name = "payment_method_name", nullable = false) // Tên phương thức thanh toán
    private String paymentMethodName; // Tên phương thức thanh toán
}
