package com.poly.Model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
@Table(name = "Discounts")
public class Discount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer discountId;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.discountCode}")
	String discountCode;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.quantity}")
	// @Min(value = 1, message = "{Min.vc.quantity}")
	@Max(value = 100, message = "{Max.vc.quantity}")
	Integer quantity;

	@ManyToOne
	@JoinColumn(name = "discountTypeId", nullable = false)
	@NotNull(message = "{NotNull.vc.discountType}") // Ensure discountType is not null
	DiscountType discountType;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.startDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	Date startDate;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.endDate}")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.DATE)
	Date endDate;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.discountValue}")
	@Min(value = 0, message = "{Min.vc.discountValue}")
	Double discountValue;

	@Column(nullable = false)
	@NotNull(message = "{NotNull.vc.minInvoiceAmount}")
	@Min(value = 0, message = "{Min.vc.minInvoiceAmount}")
	Double minInvoiceAmount;

	@Column(name = "status_id")
	Integer statusId;

//	@OneToMany(mappedBy = "discount")
//	List<DiscountDetail> DiscountDetail;

	public boolean isValid() {
		return startDate != null && endDate != null && !startDate.after(endDate);
	}

	// @ManyToOne
	// @JoinColumn(name = "status_id", nullable = false)
	// private DiscountsStatus status;
}