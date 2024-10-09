package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.model.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Integer> {

}
