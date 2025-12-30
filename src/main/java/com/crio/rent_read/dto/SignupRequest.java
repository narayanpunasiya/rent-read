package com.crio.rent_read.dto;

import com.crio.rent_read.entity.Role;
import lombok.Data;

@Data
public class SignupRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role; 
}
