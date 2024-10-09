package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.model.PaymentMethod;
import com.poly.repository.PaymentMethodRepository;

@Service
public class PaymentMethodService {
    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }
    
    public Optional<PaymentMethod> findById(Integer id) {
        return paymentMethodRepository.findById(id);
    }
}
