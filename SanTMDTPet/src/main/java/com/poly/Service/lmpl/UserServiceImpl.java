package com.poly.Service.lmpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.poly.Model.User;
import com.poly.Reponsitory.RolesRepository;
import com.poly.Reponsitory.UserRepository;
import com.poly.Service.SessionService;
import com.poly.Service.UserService;
import com.poly.dto.LoginDTO;
import com.poly.dto.RegisterDTO;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SessionService sessionService;

    @Override
    public boolean register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            return false;
        }
        User user = modelMapper.map(registerDTO, User.class);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoleId(rolesRepository.findById(2).orElse(null));
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean login(LoginDTO loginDTO) {
        if (!userRepository.existsByUsername(loginDTO.getUsername())) {
            return false;
        }
        User existingUser = userRepository.findByUsername(loginDTO.getUsername());
        if (!encoder.matches(loginDTO.getPassword(), existingUser.getPassword())) {
            return false;
        } else {
            sessionService.set("current_account", existingUser);
            sessionService.setTimeOut(1 * 24 * 60 * 60);
            return true;
        }
    }

    @Override
    public int countTotalCustomers() {
        return (int) userRepository.count(); // Assuming count() returns total user count
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getUsersByGender(Boolean gender) {
        return userRepository.findAllByGender(gender);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveOrUpdateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer username) {
        userRepository.deleteById(username);
    }

    @Override
    public int getTotalUsers() {
        return (int) userRepository.count(); // Assuming count() returns total user count
    }

    @Override
    public int getTotalProducts() {
        // Assuming there is a ProductRepository
        // return productRepository.countTotalProducts();
        return 0; // Placeholder, replace with actual implementation
    }
}
