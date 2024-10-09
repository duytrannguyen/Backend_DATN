package com.poly.serviceAPI;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.poly.model.User;
import com.poly.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsernameApi(username);
        
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        User user = optionalUser.get();
        String password = user.getPassword();

        // Check user status before granting access
        if (user.getStatusId().getStatusId() != 1) { // Assuming 1 is the 'Active' status
            throw new UsernameNotFoundException("User account is not active: " + username);
        }

        // Use roleId to get the user's role
        Set<GrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + user.getRoleId().getRoleName()));
        System.out.println("Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(user.getUsername(), password, authorities);
    }

}
