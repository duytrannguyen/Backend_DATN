package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.Model.CartItem;
import com.poly.Model.User;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    // Phương thức tìm mục giỏ hàng theo ID người dùng
    @Query("SELECT ci FROM CartItem ci WHERE ci.user = :user")
    List<CartItem> findByUser(@Param("user") User user);
}
