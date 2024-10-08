package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
