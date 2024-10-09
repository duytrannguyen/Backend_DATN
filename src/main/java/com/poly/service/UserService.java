package com.poly.service;

import com.poly.dto.request.UserRequest;
import com.poly.dto.response.Commune;
import com.poly.dto.response.District;
import com.poly.dto.response.Province;
import com.poly.dto.response.UserResponse;
import com.poly.model.Address;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;
import com.poly.model.User;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
	boolean register(RegisterDTO registerDTO);

	boolean login(LoginDTO loginDTO);

	int countTotalCustomers();

	List<User> getAllUsers();

	List<User> getUsersByGender(Boolean gender);


	void saveOrUpdateUser(User user);

	void deleteUser(Integer usersId);

	int getTotalUsers();

	int getTotalProducts();

    public String getTokenGoogle(String code);

    public User GoogleAccountGetUserInfo(String accessToken);

    public User login(UserRequest userRequest);

    public User register(UserRequest userRequest);

    public User uploadFile(MultipartFile file, Integer id);

    public User findByEmail(String email);

    public List<Province> fetchProvinces();

    public List<District> fetchDistricts(String provinceId);

    public List<Commune> fetchCommunes(String districtId);

    public boolean forgotPassword(String email);

    public User changePassword(Integer id, UserRequest userRequest);

    public User edit(Integer id, UserRequest userRequest, MultipartFile file);

    public Address addAddress(Integer id,UserRequest userRequest);

    public List<Address> getAddress(Integer id);

    public Address deleteAddress(Integer id, Integer addressId);

    User getUserByHolder();

    Resource loadImage(String fileName, HttpHeaders headers);

}
