package com.dbms.dbms_project_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbms.dbms_project_backend.dto.authentication.LoginUserDto;
import com.dbms.dbms_project_backend.dto.authentication.RegisterUserDto;
import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public User signup(RegisterUserDto input) {
        logger.info("[INFO] Signing up User with email: {}", input.getEmail());

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new FieldValueAlreadyExistsException("User", "email", input.getEmail());
        } else if (userRepository.existsByPhone(input.getPhone())) {
            throw new FieldValueAlreadyExistsException("User", "phone", input.getPhone());
        }

        User user = new User().setName(input.getName())
                .setEmail(input.getEmail())
                .setPhone(input.getPhone())
                .addRole(Role.ROLE_USER)
                .setAddress(input.getAddress())
                .setPassword(passwordEncoder.encode(input.getPassword()));

        User savedUser = userRepository.save(user);
        logger.debug("[DEBUG] User signed up successfully with email: {}", input.getEmail());
        return savedUser;
    }

    public User authenticate(LoginUserDto input) {
        logger.info("[INFO] Attempting to authenticate user with email: {}", input.getEmail());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getEmail(),
                            input.getPassword()));
        } catch (Exception e) {
            logger.error("[ERROR] Authentication failed for user with email: {}", input.getEmail(), e);
            throw e;
        }

        User user = userRepository.findByEmail(input.getEmail())
                .orElseThrow(() -> {
                    logger.warn("[WARN] User not found with email: {}", input.getEmail());
                    return new RuntimeException("User not found");
                });

        logger.debug("[DEBUG] User authenticated successfully with email: {}", input.getEmail());
        return user;
    }
}
