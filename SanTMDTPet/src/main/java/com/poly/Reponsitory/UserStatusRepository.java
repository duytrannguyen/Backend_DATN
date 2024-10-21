package com.poly.Reponsitory;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.Model.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

}
