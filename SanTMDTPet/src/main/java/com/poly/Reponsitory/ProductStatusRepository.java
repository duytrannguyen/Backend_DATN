package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.ProductStatus;
@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Integer> {
}
