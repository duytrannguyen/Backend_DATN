package com.poly.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.Nationalized;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.*;
import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "Products")
//Khang
@ToString(exclude = "invoiceItems")
public class Product {
	// ly
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(nullable = false)
	@Nationalized
	private String productName;
	
	@Column(nullable = false)
	private float discountPercentage;

	@Column(nullable = false)
	private float price;

//	@Column(nullable = false)
//	private float discountPercentage;
	
	@Column(nullable = false)
	private int publishingYear;

	@Column(nullable = false)
	@Nationalized
	private String weight;

	@Column(nullable = false)
	@Nationalized
	private String size;

	@Column(nullable = false)
	@Nationalized
	private String NumberOfPages;

	@Column(nullable = false)
	@Nationalized
	private String language;

	@Column(nullable = false)
	@Nationalized
	private String author;

	@Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
	@Nationalized
	private String description;

	@Column(nullable = false)
	@Nationalized
	private String manufacturer;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date PostingDate;

	@Column(nullable = false)
	private int quantity;

	@ManyToOne
	@JoinColumn(name = "categoryId", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "imageId", nullable = false)
	private Image imageId;
//<<<<<<< duy

	@ManyToOne
	@JoinColumn(name = "statusId", nullable = false)
	private ProductStatus status;

//=======
	
// 	@ManyToOne
// 	@JoinColumn(name = "statusId", nullable = false)
// 	private ProductStatus statusId;
	
//>>>>>>> ly
//	@OneToMany(mappedBy = "product")
//	private List<InvoiceItem> invoiceItems;
}
