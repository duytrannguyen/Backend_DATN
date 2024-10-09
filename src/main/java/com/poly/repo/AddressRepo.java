package com.poly.repo;

import com.poly.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

    @Query("""
                        select a from Address a
                        where a.users_id.usersId = ?1 and a.status = false
                        
            """)
    List<Address> findByUsers_id(Integer id);

    @Query("""
                        select a from Address a
                        where a.users_id.usersId = ?1 and a.id = ?2
                        
            """)
    Address findByUsers_idAndId(Integer userId, Integer addressId);
}
