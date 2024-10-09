package com.poly.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Nationalized;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Paymentmethods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentMethodId;

    @Column(nullable = false)
    @Nationalized
    private String paymentMethodName;
}
