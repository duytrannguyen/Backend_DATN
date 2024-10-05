package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
