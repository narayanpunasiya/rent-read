package com.crio.rent_read.service.impl;

import com.crio.rent_read.dto.SignupRequest;
import com.crio.rent_read.entity.Role;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.repositories.UserRepository;
import com.crio.rent_read.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerUser(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // BCrypt
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);

        return userRepository.save(user);
    }
}
