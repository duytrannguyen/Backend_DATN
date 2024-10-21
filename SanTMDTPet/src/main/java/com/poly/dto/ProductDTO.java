package com.poly.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.poly.Model.Image;

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
	private int yearManufacture;

	@DateTimeFormat(pattern = "yyyy-MM-dd") // Định dạng ngày tháng
	private Date PostingDate;
	private int quantity;
	private float percentDecrease;
	private float priceDecreased;
	private int categoryId;

	private String categoryName;
	private String imageUrl;
	private String statusName;
	private int sellerId;
	private String sellerName;
	private List<Image> images;

}
