package com.poly.Model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
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
// Khang
@ToString(exclude = "invoiceItems")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Product {
	// ly
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "price", nullable = false)
	private float price;

	@Column(name = "year_manufacture")
	private int yearManufacture;

	@Column(name = "size")
	private String size;

	@Column(name = "material")
	private String material;

	@Column(name = "description")
	private String description;

	@Column(name = "place_production")
	private String placeProduction;

	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date PostingDate;

	@Column(name = "quantity")
	private int quantity;

	@Column(name = "percent_decrease")
	private float percentDecrease;

	@Column(name = "price_decreased")
	private float priceDecreased;

	// @Column(name = "warranty")
	// private String warranty;

	// @Column(name = "rating")
	// private float rating;

	@ManyToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@ManyToOne
	@JoinColumn(name = "image_id", nullable = false)
	private Image imageId;

	@ManyToOne
	@JoinColumn(name = "status_id", nullable = false)
	private ProductStatus status;

	@ManyToOne
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller seller;

	// @OneToMany(mappedBy = "product")
	// private List<Review> reviews;

	// @OneToMany(mappedBy = "productId")
	// private List<CartItem> cartItems;

	// @OneToMany(mappedBy = "product")
	// private List<InvoiceItem> invoiceItems;
}
