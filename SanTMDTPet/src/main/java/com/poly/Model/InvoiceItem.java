package com.poly.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Invoiceitems") // Gắn với bảng 'InvoiceItems' trong cơ sở dữ liệu
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_item_id") // Tên cột trong cơ sở dữ liệu
    private Integer invoiceItemId; // ID mục hóa đơn

    @Column(name = "quantity", nullable = false) // Số lượng sản phẩm
    private Integer quantity;

    @Column(nullable = false) // Giá của sản phẩm
    private double price; // Giá sản phẩm

    @ManyToOne(fetch = FetchType.LAZY) // Ánh xạ đến bảng Invoices
    @JoinColumn(name = "invoice_id", nullable = false) // Tên cột trong bảng Invoices
    private Invoice invoice; // Hóa đơn tương ứng

    @ManyToOne(fetch = FetchType.LAZY) // Ánh xạ đến bảng Products
    @JoinColumn(name = "product_id", nullable = false) // Tên cột trong bảng Products
    private Product product; // Sản phẩm tương ứng

}
