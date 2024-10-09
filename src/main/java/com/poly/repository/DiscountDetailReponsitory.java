package com.poly.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.model.Discount;
import com.poly.model.DiscountDetail;
import com.poly.model.User;

@Repository
public interface DiscountDetailReponsitory extends JpaRepository<DiscountDetail, Integer> {
	Optional<DiscountDetail> findByUserAndDiscount(User user, Discount discount);

//	 boolean existsByUsersIdAndDiscountId(Integer usersId, Integer discountId);
}
