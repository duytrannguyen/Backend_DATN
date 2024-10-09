package com.poly.repo;

import com.poly.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinceRepo extends JpaRepository<Province, Integer> {


    @Query("""
                        SELECT p
                        FROM Province p
                        WHERE p.province_id = :id
                        
            """)
    Optional<Province> findProvinceByProvince_id(Integer id);
}
