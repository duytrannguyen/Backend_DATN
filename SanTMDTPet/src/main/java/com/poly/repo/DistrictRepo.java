package com.poly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.District;

@Repository
public interface DistrictRepo extends JpaRepository<District,Integer> {
}
