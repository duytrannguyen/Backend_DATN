package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
