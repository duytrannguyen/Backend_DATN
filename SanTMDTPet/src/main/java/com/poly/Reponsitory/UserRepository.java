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

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	boolean existsByUsername(String username);

//cái này cho đổi mật khẩu luôn
	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE u.username = :username")
	Optional<User> findByUsernameApi(String username);

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 3")
	List<User> findAllUsersWithUserRole3();

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 2")
	Page<User> findAllUsersWithUserRole(Pageable pageable);

	List<User> findAllByGender(Boolean gender);

	@Query("SELECT u FROM User u WHERE u.roleId.roleId = 3 AND (u.fullName LIKE %:keyword% OR u.username LIKE %:keyword%)")
	Page<User> searchUsers(String keyword, Pageable pageable);

	// Tìm người dùng bởi ID
	Optional<User> findById(Integer id);

//thêm	//bắt lỗi mail

	User findByEmail(String email);

	// Tìm người dùng theo ID
	User findByUsersId(Integer usersId);

	// Kiểm tra xem email có tồn tại trong cơ sở dữ liệu hay không
	boolean existsByEmail(String email);

}
