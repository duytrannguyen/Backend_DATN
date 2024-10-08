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
    private int yearManufacture;
    private String size;
    private String material;
    private String description;
    private String placeProduction;
    private Date PostingDate;
    private int quantity;
    private float percentDecrease;
    private float priceDecreased;

    // Bạn có thể thêm các thuộc tính khác từ các đối tượng liên quan như category, imageId, status, seller nếu cần
    // Ví dụ:
    private String categoryName;  // nếu bạn muốn bao gồm tên thể loại
    private String imageUrl;      // nếu bạn muốn bao gồm URL hình ảnh
    private String statusName;     // nếu bạn muốn bao gồm tên trạng thái
    private String sellerName;     // nếu bạn muốn bao gồm tên người bán
}
