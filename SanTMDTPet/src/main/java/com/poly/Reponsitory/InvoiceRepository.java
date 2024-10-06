package com.poly.Reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Invoice;

@Repository("InvoiceRepository")
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	// Truy vấn để lấy danh sách hóa đơn theo user_id
    List<Invoice> findByUser(Integer user);
}
