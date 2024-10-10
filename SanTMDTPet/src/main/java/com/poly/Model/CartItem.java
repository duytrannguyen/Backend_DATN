package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Cartitems") // Gắn với bảng 'Cartitems' trong cơ sở dữ liệu
public class CartItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_item_id") // Tên cột trong cơ sở dữ liệu
	private Integer cartItemId; // ID mục giỏ hàng

	@Column(name = "quantity", nullable = false) // Số lượng sản phẩm
	private Integer quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false) // Tên cột trong bảng Products
	private Product product; // Mã sản phẩm

	@ManyToOne(fetch = FetchType.LAZY) // Ánh xạ đến bảng Users
	@JoinColumn(name = "users_id", nullable = false) // Tên cột trong bảng Users
	private User user; // Người dùng sở hữu giỏ hàng
}
