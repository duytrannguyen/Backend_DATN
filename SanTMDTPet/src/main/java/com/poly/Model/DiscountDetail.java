package com.poly.Model;

import java.util.Date;
import jakarta.persistence.*;

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
@Table(name = "Discountdetails") // Gắn với bảng 'DiscountDetails' trong cơ sở dữ liệu
public class DiscountDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "voucher_detail_id") // Tên cột trong cơ sở dữ liệu
	private Integer discountDetailId; // ID chi tiết giảm giá

	@ManyToOne
	@JoinColumn(name = "discount_id", nullable = false) // Ánh xạ đến bảng Discounts
	private Discount discount; // Giảm giá

	// @ManyToOne
	// @JoinColumn(name = "users_id", nullable = false) // Ánh xạ đến bảng Users
	// private User user; // Người dùng
	// @ManyToOne
	// @JoinColumn(name = "users_id", nullable = false) // Ánh xạ đến bảng Users
	// private User user; // Người dùng

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = false) // Ánh xạ đến bảng Products
	private Product product; // Sản phẩm (bạn cần tạo class Product nếu chưa có)

	@Temporal(TemporalType.DATE)
	@Column(name = "used_date") // Tên cột trong cơ sở dữ liệu
	private Date usedDate; // Ngày sử dụng

}
