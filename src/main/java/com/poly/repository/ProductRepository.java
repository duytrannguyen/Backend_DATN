package com.poly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	@Query("SELECT COUNT(p) FROM Product p")
	int countTotalProducts();

//	@Query("SELECT p.productName, i.quantity, os.statusName " + "FROM Product p " + "JOIN p.invoiceItems i "
//			+ "JOIN i.invoice iv " + "JOIN iv.status os " + "WHERE p.productName IS NOT NULL")
//	List<Object[]> findProductNamesWithStatusAndQuantity();

	Optional<Product> findByProductId(Integer productId);

//	boolean existsByProductName(String productName);
//	lọc sản phẩm
	List<Product> findByCategoryCategoryId(int categoryId);
	Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);
	 
	 Page<Product> findByProductNameContaining(String productName, Pageable pageable);
	 Page<Product> findByCategory(String category, Pageable pageable);
	 
	@Query("SELECT p FROM Product p WHERE p.category.categoryId =:categoryId")
   Page<Product> findProductsByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);
	 // Tìm sản phẩm theo tên
    Optional<Product> findByProductName(String productName);

    // Kiểm tra sự tồn tại của sản phẩm theo tên
    boolean existsByProductName(String productName);

    // Tìm sản phẩm theo ID
    Optional<Product> findById(Integer productId);

    // Kiểm tra sự tồn tại của sản phẩm theo ID
    boolean existsById(Integer productId);

    // Xóa sản phẩm theo ID
//    void deleteById(Integer productId);
}
