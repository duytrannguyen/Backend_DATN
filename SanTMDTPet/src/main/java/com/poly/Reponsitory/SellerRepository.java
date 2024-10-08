package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Seller;
@Repository("SellerRepository")
public interface SellerRepository extends JpaRepository<Seller, Integer>{

}
