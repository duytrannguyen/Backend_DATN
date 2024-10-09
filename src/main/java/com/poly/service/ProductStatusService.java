package com.poly.service;

import com.poly.model.ProductStatus;
import com.poly.repository.ProductStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductStatusService {

    private final ProductStatusRepository productStatusRepository;

    @Autowired
    public ProductStatusService(ProductStatusRepository productStatusRepository) {
        this.productStatusRepository = productStatusRepository;
    }

    // Phương thức để lấy tất cả các trạng thái sản phẩm
    public List<ProductStatus> findAll() {
        return productStatusRepository.findAll();
    }

    // Phương thức để tìm trạng thái sản phẩm theo ID
    public ProductStatus findByStatusId(int statusId) {
        return productStatusRepository.findById(statusId).orElse(null);
    }

    // Phương thức để lưu trạng thái sản phẩm
    public void save(ProductStatus productStatus) {
        productStatusRepository.save(productStatus);
    }

    // Phương thức để xóa trạng thái sản phẩm theo ID
    public void deleteById(int statusId) {
        productStatusRepository.deleteById(statusId);
    }
}
