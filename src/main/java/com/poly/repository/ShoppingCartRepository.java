package com.poly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.poly.model.CartItem;
import com.poly.model.Product;
import com.poly.model.ShoppingCart;
import com.poly.model.User;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer> {
// <<<<<<< Bich_Di
	
    ShoppingCart findByUser(User user);
//    
//// =======
//
//	@Query("select c from ShoppingCart c where c.user.username =:username")
//	public ShoppingCart findByAccountId(String username);
////
//	// ly
//	List<ShoppingCart> findByUser(User user);
//
	ShoppingCart findCurrentCartByUser(User user);
// >>>>>>> dev
	
	//ly
	List<ShoppingCart> findByCartId(Integer cartId);
}
