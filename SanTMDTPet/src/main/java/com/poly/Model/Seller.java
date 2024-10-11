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

	    @Column(name = "shop_name", length = 50)
	    private String shopName;

	    @Column(name = "avt_shop", length = 255)
	    private String avtShop;

	    @Column(name = "backround", length = 255)
	    private String backround;

	    @Column(name = "type_business", length = 255)
	    private String typeBusiness;

	    @Column(name = "tax_code")
	    private Integer taxCode;

	    @Column(name = "cccd_cmnd", length = 12)
	    private String cccdCmnd;

	    @Column(name = "front_CCCD", length = 255)
	    private String frontCCCD;

	    @Column(name = "back_CCCD", length = 255)
	    private String backCCCD;

	    @Column(name = "status", length = 50)
	    private String status = "PENDING";

	    @ManyToOne
	    @JoinColumn(name = "users_id", nullable = false)
	    private User user;

}
