package com.poly.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

    @Query("SELECT SUM(i.totalAmount) FROM Invoice i")
    Double findTotalAmount();

    @Query("SELECT COUNT(i) FROM Invoice i")
    Long countTotalOrders();

    @Query("SELECT i FROM Invoice i JOIN FETCH i.user u JOIN FETCH i.status s JOIN FETCH i.paymentMethod pm JOIN FETCH i.shipping sh LEFT JOIN FETCH i.discount d")
    List<Invoice> findAllInvoicesWithDetails();

    @Query("SELECT i FROM Invoice i WHERE (:status = 'ALL' OR i.status.statusName = :status) AND (i.user.username LIKE %:key% OR :key IS NULL)")
    Page<Invoice> findByStatusAndKey(@Param("status") String status, @Param("key") String key, Pageable pageable);

    Page<Invoice> findAll(Pageable pageable);
}
