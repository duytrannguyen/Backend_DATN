package com.poly.model;

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
@Table(name = "Invoiceitems")
public class InvoiceItem {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "invoice_item_id")
	    private Integer invoiceItemId;

	    @Column(name = "quantity", nullable = false)
	    private Integer quantity;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "invoice_id", nullable = false)
	    private Invoice invoice;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "product_id", nullable = false)
	    private Product product;
	    
	    //ly
	    @Column(nullable = false)
		private double price;

}