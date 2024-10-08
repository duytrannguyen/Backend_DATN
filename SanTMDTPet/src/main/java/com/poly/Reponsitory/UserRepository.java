package com.poly.Reponsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.poly.Model.User;


@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsername(String username);

//cái này cho đổi mật khẩu luôn
	User findByUsername(String username);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findByUsernameApi(String username);
//	Optional<User> findByUsernameApi(String username);

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2")
	List<User> findAllUsersWithUserRole();

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2")
	Page<User> findAllUsersWithUserRole(Pageable pageable);

	List<User> findAllByGender(Boolean gender);

//lọc
	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2")
	Page<User> findAllUsersWithRoleId2(Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2 AND u.gender = :gender")
	Page<User> findByGenderAndRoleId(Boolean gender, Pageable pageable);
//	Page<User> findByGenderAndRoleId(Boolean gender, Integer roleId, Pageable pageable);
//
//	Page<User> findByRoleId(Integer roleId, Pageable pageable);

//	Tìm kiếm
	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2 AND u.fullName LIKE %:keyword%")
	Page<User> searchUsersWithRoleId2(String keyword, Pageable pageable);

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2 AND u.gender = :gender AND u.fullName LIKE %:keyword%")
	Page<User> searchByGenderAndRoleId(String keyword, Boolean gender, Pageable pageable);

	// Tìm người dùng bởi ID
	Optional<User> findById(Integer id);

//thêm	//bắt lỗi mail
	User findByEmail(String email);

}
