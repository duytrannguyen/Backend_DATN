package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Invoice;
import com.poly.Model.User;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	// // Truy vấn để lấy danh sách hóa đơn theo user_id
	List<Invoice> findByUser_UsersId(Integer usersId);

	Optional<Invoice> findById(Integer invoiceId);
}