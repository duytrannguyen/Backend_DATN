package com.poly.dto;

import java.util.Date;
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
public class InvoiceDTO {
    private Integer invoiceId;
    private double totalAmount;
    private double feeShip;
    private String fullAddress;
    private String orderStatusName;
    private Date paymentDate;
    private Integer usersId;
    private Integer orderStatusId;
    private Integer paymentMethodId;
    private Integer discountCodeId;
    private List<InvoiceItemDTO> invoiceItems;
}
