package com.poly.service;

import org.springframework.stereotype.Service;

@Service
public class CartService {

    private double totalAmount;
    private double originalTotalAmount; // Tổng tiền chưa giảm giá

    public void initializeCart(double initialAmount) {
        this.totalAmount = initialAmount;
        this.originalTotalAmount = initialAmount;
    }

    public void applyDiscount(double discountValue) {
        totalAmount -= discountValue;
        if (totalAmount < 0) {
            totalAmount = 0;
        }
    }

    public void removeDiscount() {
        totalAmount = originalTotalAmount; // Khôi phục tổng tiền chưa giảm giá
    }

    public double getCurrentTotal() {
        return originalTotalAmount;
    }

    public double getOriginalTotalAmount() {
        return originalTotalAmount;
    }
}


