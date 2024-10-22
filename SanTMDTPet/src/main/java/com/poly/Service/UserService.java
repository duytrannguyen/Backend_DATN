package com.poly.Service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

import com.poly.Model.Address;
import com.poly.Model.User;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.dto.UserDTO;
import com.poly.dto.request.UserRequest;

public interface UserService {
    boolean register(RegisterDTO registerDTO);

    boolean login(LoginDTO loginDTO);

    int countTotalCustomers();

    List<User> getAllUsers();

    List<User> getUsersByGender(Boolean gender);

    User getUserByUsername(String username); // Thêm phương thức này

    void saveOrUpdateUser(User user);

    void deleteUser(Integer usersId);

    int getTotalUsers();

    int getTotalProducts();

    // Cập nhật vai trò người dùng thành seller
    void updateUserRoleToSeller(int userId);

    // Cập nhật người dùng
    void updateUser(UserDTO userDTO);

    // Tìm kiếm khách hàng theo tên hoặc username với phân trang
    Page<User> searchUsers(String keyword, Pageable pageable);

    // Các phương thức bổ sung từ đoạn mã thứ hai
    public String getTokenGoogle(String code);

    public User GoogleAccountGetUserInfo(String accessToken);

    public User login(UserRequest userRequest);

    public User register(UserRequest userRequest);

    public User uploadFile(MultipartFile file, Integer id);

    public User findByEmail(String email);

    public boolean forgotPassword(String email);

    public User changePassword(Integer id, UserRequest userRequest);

    public User edit(Integer id, UserRequest userRequest, MultipartFile file);

    public Address addAddress(Integer id, UserRequest userRequest);

    public List<Address> getAddress(Integer id);

    public Address deleteAddress(Integer id, Integer addressId);

    User getUserByHolder();

    Resource loadImage(String fileName, HttpHeaders headers);
    
    // Thêm phương thức tìm kiếm người dùng theo username
    User findByUsername(String username); // Thêm phương thức này
}
