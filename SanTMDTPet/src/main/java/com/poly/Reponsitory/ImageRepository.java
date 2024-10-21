package com.poly.Reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.Model.Image;
import com.poly.Model.Product;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
    // Tìm hình ảnh theo productId, trả về danh sách hình ảnh
    List<Image> findByProductId(Integer productId);

    // Tìm hình ảnh theo productId, trả về một hình ảnh
    Image findFirstByProductId(Integer productId); // Sử dụng findFirst để lấy một hình ảnh duy nhất

    // Đếm số lượng hình ảnh theo productId
    long countByProductId(Integer productId);
}
