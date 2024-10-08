package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.Invoice;
import com.poly.Model.ShoppingCart;
import com.poly.Model.User;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
    // Optional<ShoppingCart> findByUser(User user);
    // Optional<ShoppingCart> findByUser_UsersId(Integer usersId);

    // Tìm giỏ hàng của người dùng
    Optional<ShoppingCart> findByUser_UsersId(Integer userId);
}
