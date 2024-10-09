package com.poly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Province;

@Repository
public interface ProvinceRepo extends JpaRepository<Province, Integer> {
}
