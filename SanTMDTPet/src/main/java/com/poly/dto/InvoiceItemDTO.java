package com.poly.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InvoiceItemDTO {
    private Integer invoiceItemId; // ID của InvoiceItem
    private double price; // Giá sản phẩm
    private int quantity; // Số lượng
    private List<ProductDTO> products;
}
