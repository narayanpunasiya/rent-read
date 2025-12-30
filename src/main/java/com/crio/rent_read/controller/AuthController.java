package com.crio.rent_read.controller;

import com.crio.rent_read.dto.LoginRequest;
import com.crio.rent_read.dto.UserResponse;
import com.crio.rent_read.entity.Role;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Common method for signup
    private UserResponse saveUser(User user, Role defaultRole) {
        if (user.getRole() == null) {
            user.setRole(defaultRole);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userRepository.save(user);
        return new UserResponse(saved.getId(), saved.getFirstName(), saved.getLastName(),
                saved.getEmail(), saved.getRole());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody User user) {
        UserResponse response = saveUser(user, Role.USER);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/signup/admin")
    public ResponseEntity<UserResponse> registerAdmin(@RequestBody User user) {
        UserResponse response = saveUser(user, Role.ADMIN);
        return ResponseEntity.status(201).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        } catch (AuthenticationException e) {
            throw new RuntimeException("Authentication failed");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = new UserResponse(user.getId(), user.getFirstName(),
                user.getLastName(), user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);
    }
}
