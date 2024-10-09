package com.poly.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poly.model.OrderStatus;
import com.poly.repository.OrderStatusRepository;

@Service
public class StatusService {
    @Autowired
    private OrderStatusRepository orderStatusRepository;

    public List<OrderStatus> findAll() {
        return orderStatusRepository.findAll();
    }
    
    public Optional<OrderStatus> findById(Integer id) {
        return orderStatusRepository.findById(id);
    }
}

