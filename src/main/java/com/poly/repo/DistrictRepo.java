package com.poly.repo;

import com.poly.model.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DistrictRepo extends JpaRepository<District,Integer> {


    @Query("""
                        SELECT d
                        FROM District d
                        WHERE d.district_id = :id
                        
            """)
    Optional<District> findDistrictByDistrict_id(Integer id);
}
