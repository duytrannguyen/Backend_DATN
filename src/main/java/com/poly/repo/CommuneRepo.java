package com.poly.repo;

import com.poly.model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommuneRepo extends JpaRepository<Commune,Integer> {
}
