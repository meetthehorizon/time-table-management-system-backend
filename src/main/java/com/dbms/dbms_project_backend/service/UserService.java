package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.dto.RegisterUserDto;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public List<User> findAll() {
        logger.debug("[DEBUG] Fetching all users");
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        logger.debug("[DEBUG] Fetching user with id: {}", id);
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            logger.info("[INFO] User found with id: {}", id);
        } else {
            logger.warn("[WARN] User not found with id: {}", id);
        }
        return user;
    }

    public User createUser(User user) {
        logger.debug("[DEBUG] Creating user with email: {}", user.getEmail());
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName(user.getName());
        registerUserDto.setEmail(user.getEmail());
        registerUserDto.setPhone(user.getPhone());
        registerUserDto.setAddress(user.getAddress());
        registerUserDto.setPassword(user.getPassword());

        try {
            user = authenticationService.signup(registerUserDto);
            logger.info("[INFO] User created with email: {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Error creating user with email: {}", user.getEmail(), e);
        }
        return user;
    }

    public User updateUser(User user) {
        logger.debug("[DEBUG] Updating user with id: {}", user.getId());
        try {
            user = userRepository.update(user);
            logger.info("[INFO] User updated with id: {}", user.getId());
        } catch (Exception e) {
            logger.error("Error updating user with id: {}", user.getId(), e);
        }
        return user;
    }

    public void deleteUser(Long id) {
        logger.debug("[DEBUG] Deleting user with id: {}", id);
        try {
            userRepository.deleteById(id);
            logger.info("[INFO] User deleted with id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting user with id: {}", id, e);
        }
    }

    public Optional<User> findByEmail(String email) {
        logger.debug("[DEBUG] Fetching user with email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            logger.info("[INFO] User found with email: {}", email);
        } else {
            logger.warn("[WARN] User not found with email: {}", email);
        }
        return user;
    }

    public List<User> findUsersByRole(Role role) {
        logger.debug("[DEBUG] Fetching users with role: {}", role);
        return userRepository.findUsersByRole(role);
    }

}
