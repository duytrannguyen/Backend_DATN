package com.poly.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.Model.Commune;

@Repository
public interface CommuneRepo extends JpaRepository<Commune,Integer> {
}
