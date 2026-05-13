package com.edutech.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.edutech.entity.Role;
import com.edutech.entity.User;
import com.edutech.exception.DuplicateResourceException;
import com.edutech.exception.ResourceNotFoundException;
import com.edutech.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * Used by Spring Security during authentication/login.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );

        Role role = user.getRole();

        if (role == null) {
            role = Role.ADMIN;
        }

        String roleName = role.name();

        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        List<SimpleGrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }

    /*
     * Register user with encoded password.
     */
    public User registerUser(User user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (user.getContactNumber() != null &&
                userRepository.existsByContactNumber(user.getContactNumber())) {
            throw new DuplicateResourceException("Contact number already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (user.getRole() == null) {
            user.setRole(Role.ADMIN);
        }

        return userRepository.save(user);
    }

    /*
     * Alias method if controller/test calls register().
     */
    public User register(User user) {
        return registerUser(user);
    }

    public User getUserByUsername(String username) {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with username: " + username)
                );
    }

    public User getUserById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id)
                );
    }

    /*
     * Used for /api/auth/user endpoint.
     */
    public User getLoggedInUser(String username) {
        return getUserByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByContactNumber(Long contactNumber) {
        return userRepository.existsByContactNumber(contactNumber);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}