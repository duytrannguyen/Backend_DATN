package com.poly.Model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Discounts") // Gắn với bảng 'Discounts' trong cơ sở dữ liệu
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer discountId; // ID giảm giá

	@Column(nullable = false, length = 50) // Giới hạn chiều dài của discountCode
	// @NotNull(message = "{NotNull.vc.discountCode}")
	private String discountCode; // Mã giảm giá

	@Column(nullable = false)
	// @NotNull(message = "{NotNull.vc.quantity}")
	// @Min(value = 1, message = "{Min.vc.quantity}")
	// @Max(value = 100, message = "{Max.vc.quantity}")
	private Integer quantity; // Số lượng giảm giá

	@Column(nullable = false)
	// @NotNull(message = "{NotNull.vc.startDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date startDate; // Ngày bắt đầu giảm giá

	@Column(nullable = false)
	// @NotNull(message = "{NotNull.vc.endDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	private Date endDate; // Ngày kết thúc giảm giá

	@Column(nullable = false)
	// @NotNull(message = "{NotNull.vc.discountValue}")
	// @Min(value = 0, message = "{Min.vc.discountValue}")
	private Double discountValue; // Giá trị voucher

	@Column(nullable = false)
	// @NotNull(message = "{NotNull.vc.minInvoiceAmount}")
	// @Min(value = 0, message = "{Min.vc.minInvoiceAmount}")
	private Double minInvoiceAmount; // Số tiền hóa đơn tối thiểu để áp dụng giảm giá

	@Column(name = "status_id", nullable = false) // Trường status_id, không được null
	private Integer statusId; // ID trạng thái giảm giá

}
