package com.crio.rent_read.service;

import com.crio.rent_read.dto.SignupRequest;
import com.crio.rent_read.entity.User;

public interface AuthService {
    User registerUser(SignupRequest request);
}
