package com.poly.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.Model.Seller;
import com.poly.Reponsitory.SellerRepository;

@Service
public class SellerService {
    @Autowired
    private SellerRepository sellerRepository;

    // Kiểm tra người bán có tồn tại hay không
    public Seller findSellerById(int sellerId) {
        return sellerRepository.findById(sellerId).orElse(null);
    }
}
