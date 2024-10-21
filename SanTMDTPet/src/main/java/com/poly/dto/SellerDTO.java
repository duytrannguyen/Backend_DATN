package com.poly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerDTO {
	private int sellerId;
    private String shopName;
    private String avtShop;
    private String backround;
    private String typeBusiness;
    private String taxCode;
    private String cccdCmnd;
    private String frontCCCD;
    private String backCCCD;
    private String status;
    private int usersId; // Giữ ID của user thay vì đối tượng User
    
    // Bạn có thể thêm các trường khác nếu cần
}
