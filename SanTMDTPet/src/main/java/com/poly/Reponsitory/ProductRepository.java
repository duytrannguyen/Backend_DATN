package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.Model.Product;
import com.poly.Model.Seller;

@Repository("ProductRepository")
public interface ProductRepository extends JpaRepository<Product, Integer> {
    // Page<Product> findByProductNameContaining(String keyName, Pageable pageable);
    //
    // Page<Product> findProductsByCategory(Integer categoryId, Pageable pageable);

    List<Product> findBySeller_SellerId(int sellerId);

    @Query("SELECT COUNT(p) FROM Product p")
    int countTotalProducts();

    // @Query("SELECT p.productName, i.quantity, os.statusName " +
    // "FROM Product p " +
    // "JOIN p.invoiceItems i " +
    // "JOIN i.invoice iv " +
    // "JOIN iv.status os " +
    // "WHERE p.productName IS NOT NULL")
    // List<Object[]> findProductNamesWithStatusAndQuantity();

    Optional<Product> findByProductId(Integer productId);

    boolean existsByProductName(String productName);

    // Lọc sản phẩm theo categoryId
    List<Product> findByCategoryCategoryId(int categoryId);

    // Tìm sản phẩm theo khoảng giá
    Page<Product> findByPriceBetween(double minPrice, double maxPrice, Pageable pageable);

    // Tìm sản phẩm theo tên chứa chuỗi
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    Page<Product> findProductsByCategory(@Param("categoryId") Integer categoryId, Pageable pageable);

    // Tìm sản phẩm theo seller
    Page<Product> findBySeller(Seller seller, Pageable pageable); // Sử dụng đối tượng Seller

    // repost
    // Lấy danh sách sản phẩm của seller dựa trên sellerId
    List<Product> findBySeller_sellerId(int sellerId);

    @Query("SELECT p FROM Product p " +
            "JOIN InvoiceItem i ON p.id = i.product.id " +
            "JOIN i.invoice inv " +
            "WHERE p.seller.sellerId = :sellerId " +
            "GROUP BY p.productId, p.productName, p.price, p.size, p.material, p.description, " +
            "p.placeProduction, p.postingDate, p.quantity, p.category.categoryId, p.imageId, p.status, p.seller.sellerId "
            +
            "ORDER BY SUM(i.quantity) DESC")
    List<Product> findTop3BySellerId(@Param("sellerId") int sellerId, Pageable pageable);

    // Đếm tổng số sản phẩm của seller dựa trên sellerId
    int countBySeller_sellerId(int sellerId);
}
