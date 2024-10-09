package com.poly.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "DiscountDetail")
public class DiscountDetail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Integer discount_detail_id;

    @ManyToOne
    @JoinColumn(name = "discount_id")
     Discount discount;

    @ManyToOne
    @JoinColumn(name = "users_id")
     User user;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
     Invoice invoice;

    @Temporal(TemporalType.DATE)
     Date usedDate;
}

