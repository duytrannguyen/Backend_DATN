package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.Model.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	// Lấy danh sách hóa đơn theo người dùng
	List<Invoice> findByUser_UsersId(Integer usersId);

	// Lọc hoá đơn sản phẩm theo Status
	@Query("SELECT i FROM Invoice i JOIN i.invoiceItems id JOIN id.product p JOIN p.seller s WHERE s.id = :sellerId AND i.status.statusName IN :orderStatusNames")
	List<Invoice> findBySellerIdAndStatusNames(Integer sellerId, List<String> orderStatusNames);

	// report
	// Đếm tổng số đơn hàng của seller dựa trên sellerId
	@Query("SELECT COUNT(i) FROM Invoice i")
	Long countBySeller_sellerId(@Param("sellerId") int sellerId);

	// Tính tổng số tiền từ các đơn hàng của seller dựa trên sellerId
	@Query("SELECT SUM(i.totalAmount) FROM Invoice i")
	Double findTotalAmountBySeller_sellerId(@Param("sellerId") int sellerId);

}
