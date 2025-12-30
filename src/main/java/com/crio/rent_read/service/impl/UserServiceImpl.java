package com.crio.rent_read.service.impl;

import com.crio.rent_read.entity.User;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repositories.UserRepository;
import com.crio.rent_read.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
