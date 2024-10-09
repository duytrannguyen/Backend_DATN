package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.model.ProductStatus;
@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Integer> {
}
