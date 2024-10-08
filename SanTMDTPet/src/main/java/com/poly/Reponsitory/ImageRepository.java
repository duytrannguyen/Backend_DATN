package com.poly.Reponsitory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.Model.Image;
import com.poly.Model.Product;



@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
	@Query("SELECT p FROM Product p WHERE p.imageId.imageId = :imageId")
	List<Product> findProductsByImageId(Integer imageId);

}