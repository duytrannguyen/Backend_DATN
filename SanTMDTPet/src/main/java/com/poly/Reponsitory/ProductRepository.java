package com.poly.Reponsitory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	Page<Product> findByProductNameContaining(String keyName, Pageable pageable);

	Page<Product> findProductsByCategory(Integer categoryId, Pageable pageable);

	List<Product> findBySeller_SellerId(int sellerId);
}
