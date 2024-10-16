package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.Model.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Integer> {
        // Phương thức lấy tất cả voucher với phân trang
        Page<Discount> findAll(Pageable pageable);

        // Phương thức tìm kiếm voucher theo key (có thể tìm theo mã hoặc tên voucher)
        Page<Discount> findBydiscountCode(String key, Pageable pageable);

        @Query("SELECT v FROM Discount v WHERE v.statusId = :statusId")
        Page<Discount> findByStatusId(@Param("statusId") int statusId, Pageable pageable);

        @Query("SELECT d FROM Discount d WHERE d.statusId IN :statusList")
        Page<Discount> findByStatusIdIn(@Param("statusList") List<Integer> statusList, Pageable pageable);

        // Tự động cập nhật trạng thái voucher
        // voucher hết hạn
        @Query("SELECT v FROM Discount v WHERE v.endDate < :currentDate AND v.statusId<>3")
        List<Discount> findByEndDateBeforeAndStatusId(
                        @Param("currentDate") LocalDate currentDate);

        // voucher chờ phát hành
        @Query("SELECT v FROM Discount v WHERE v.startDate > :currentDate AND v.statusId<>2")
        List<Discount> findByStartDateAfterAndStatusId(
                        @Param("currentDate") LocalDate currentDate);

        // voucher dùng hết
        @Query("SELECT d FROM Discount d WHERE d.startDate = :currentDate AND d.quantity>0 AND d.statusId<>1")

        List<Discount> findByStartDateNowAndQuantityGreaterThanZero(@Param("currentDate") LocalDate currentDate);

        List<Discount> findByQuantityLessThanEqual(int quantity);

        // Discount findByDiscountCode(String discountCode);
        Optional<Discount> findByDiscountCode(String discountCode);
        // Tìm mã khuyến mãi dựa trên mã khuyến mãi
        // Optional<Discount> findByCode(String code);

}