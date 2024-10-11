package com.poly.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
	private int productId;
	private String productName;
	private float price;
	private String size;
	private String material;
	private String description;
	private String placeProduction;
	private Date PostingDate;
	private int quantity;

	private String categoryName;
	private String imageUrl;
	private String statusName;
	private int sellerId;
}
