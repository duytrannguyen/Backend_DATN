package com.poly.repository;

import java.util.List;

// <<<<<<< Bich_Di
import org.springframework.data.domain.Sort;
// =======
// >>>>>>> dev
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.poly.model.CartItem;
//<<<<<<< Bich_Di  
import com.poly.model.Product;
//=======
//>>>>>>> dev
import com.poly.model.ShoppingCart;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
	
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart, Sort sort);

// <<<<<<< Bich_Di
    @Query("select c from CartItem c where c.shoppingCart = :cart and c.productId.productId = :productId")
    CartItem findByCartIdAndProductId(@Param("cart") ShoppingCart cart, @Param("productId") Integer productId, Sort sort);
// =======
//	@Query("select c from CartItem c where c.cartItemId.cartItemId=:cartItemId and c.productId.productId = :productId")
//	public CartItem findByCartIdAndProductId(Integer cartId, Integer productId);
	
	
	//ly
	 List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
// >>>>>>> dev
}

