package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {

}
