//package com.poly.repository;
//
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//public interface VoucherRepository extends JpaRepository<Voucher, Integer> {
//	@Query("SELECT v FROM Voucher v WHERE v.discountType.discountTypeId = :discountTypeId")
//	Page<Voucher> findByDiscountTypeId(@Param("discountTypeId") int discountTypeId,Pageable pageable);
//
//	@Query("SELECT p FROM Voucher p WHERE p.discountCode like %:discountCode%")
//    Page<Voucher> findByDiscountCode(@Param("discountCode") String discountCode, Pageable pageable);
//}
