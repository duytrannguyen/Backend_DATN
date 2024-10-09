//package com.poly.model;
//
//import java.util.Date;
//
//import com.fasterxml.jackson.annotation.JsonFormat;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.Table;
//import jakarta.persistence.Temporal;
//import jakarta.persistence.TemporalType;
//import jakarta.validation.constraints.Max;
//import jakarta.validation.constraints.Min;
//import jakarta.validation.constraints.NotNull;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Data
//@Entity
//@NoArgsConstructor
//@AllArgsConstructor
//@Getter
//@Setter
//@Table(name = "Discounts")
//public class Voucher {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	Integer discountId;
//
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.discountCode}")
//	String discountCode;
//
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.quantity}")
//	@Min(value = 1, message = "{Min.vc.quantity}")
//	@Max(value = 100, message = "{Max.vc.quantity}")
//	Integer quantity;
//
//	@ManyToOne
//	@JoinColumn(name = "discountTypeId", nullable = false)
//	@NotNull(message = "{NotNull.vc.discountType}") // Ensure discountType is not null
//	DiscountType discountType;
//
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.startDate}")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	@Temporal(TemporalType.DATE) 
//	Date startDate;
//
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.endDate}")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	@Temporal(TemporalType.DATE)
//	Date endDate;
//	
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.discountValue}")
//	@Min(value = 0, message = "{Min.vc.discountValue}")
//	Double discountValue;
//	
//	@Column(nullable = false)
//	@NotNull(message = "{NotNull.vc.minInvoiceAmount}")
//	@Min(value = 0, message = "{Min.vc.minInvoiceAmount}")
//	Double minInvoiceAmount;
//	
//	public boolean isValid() {
//		return startDate != null && endDate != null && !startDate.after(endDate);
//	}
//
//}
