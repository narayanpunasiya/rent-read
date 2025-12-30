package com.crio.rent_read.service;

import com.crio.rent_read.entity.User;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);
    List<User> getAllUsers();
}
