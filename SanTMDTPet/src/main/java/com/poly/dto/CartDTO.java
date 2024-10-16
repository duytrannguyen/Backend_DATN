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
public class CartDTO {
    private Integer user;
    private Integer quantity;
    private Integer cartItemId;
    private List<ProductDTO> products;
}
