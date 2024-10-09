package com.poly.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.poly.model.Image;
import com.poly.model.Product;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {
	@Query("SELECT p FROM Product p WHERE p.imageId.imageId = :imageId")
	List<Product> findProductsByImageId(Integer imageId);

}