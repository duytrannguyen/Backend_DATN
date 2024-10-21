package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
	   // Tìm danh mục theo tên
    Category findByCategoryName(String categoryName);

    // Phương thức kiểm tra xem danh mục đã tồn tại theo tên hay chưa
    boolean existsByCategoryName(String categoryName);
    // Thêm phương thức để kiểm tra sự tồn tại của danh mục theo ID
    boolean existsByCategoryId(Integer categoryId);
}
