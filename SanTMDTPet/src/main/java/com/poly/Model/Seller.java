package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_id")
    private int sellerId;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "avt_shop")
    private String avtShop;

    @Column(name = "background")
    private String background;

    @Column(name = "type_business")
    private String typeBusiness;

    @Column(name = "tax_code")
    private String taxCode;

    @Column(name = "cccd_cmnd")
    private String cccdCmnd;

    @Column(name = "front_cccd")
    private String frontCccd;

    @Column(name = "back_cccd")
    private String backCccd;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

//    @OneToMany(mappedBy = "seller")
//    private List<Product> products;
}
