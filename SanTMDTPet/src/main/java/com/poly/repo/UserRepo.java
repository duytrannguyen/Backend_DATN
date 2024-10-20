package com.poly.repo;

import com.poly.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    Boolean existsByEmail(String email);

    User findByEmail(String email);

    User findByUsersId(Integer id);

    User findByUsername(String userName);
}
