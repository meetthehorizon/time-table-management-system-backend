package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.dto.RegisterUserDto;
import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
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
        logger.info("[INFO] Fetching all Users");
        List<User> users = userRepository.findAll();

        logger.debug("[DEBUG] Fetched all Users");
        return users;
    }

    public User createUser(User user) {
        logger.info("[INFO] Creating user with email: {}", user.getEmail());
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName(user.getName());
        registerUserDto.setEmail(user.getEmail());
        registerUserDto.setPhone(user.getPhone());
        registerUserDto.setAddress(user.getAddress());
        registerUserDto.setPassword(user.getPassword());

        user = authenticationService.signup(registerUserDto);

        return user;
    }

    public User updateUser(User user) {
        logger.info("[INFO] Updating user with id: {}", user.getId());

        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User", "userId", user.getId()));

        logger.info("user: {}", user);
        logger.info("existingUser: {}", existingUser);

        if (!existingUser.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(user.getEmail())) {
                logger.debug("[DEBUG] User already exists with email: {}", user.getEmail());
                throw new FieldValueAlreadyExistsException("User", "email", user.getEmail());
            }
        }

        if (!existingUser.getPhone().equals(user.getPhone())) {
            if (userRepository.existsByPhone(user.getPhone())) {
                logger.debug("[DEBUG] User already exists with phone: {}", user.getPhone());
                throw new FieldValueAlreadyExistsException("User", "phone", user.getPhone());
            }
        }

        User updatedUser = userRepository.update(user);

        logger.debug("[DEBUG] User updated with id: {}", updatedUser.getId());
        return updatedUser;
    }

    public void deleteUser(Long id) {
        logger.info("[INFO] Deleting user with id: {}", id);
        userRepository.deleteById(id);

        logger.debug("[DEBUG] User deleted with id: {}", id);
    }

    public User findById(Long id) {
        logger.info("[INFO] Fetching user with id: {}", id);
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()) {
            logger.debug("[INFO] User found with id: {}", id);
            return user.get();
        } else {
            throw new NotFoundException("User", "userId", id);
        }
    }

    public Optional<User> findByEmail(String email) {
        logger.info("[INFO] Fetching user with email: {}", email);
        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            logger.debug("[DEBUG] User found with email: {}", email);
            return user;
        } else {
            throw new NotFoundException("User", "email", email);
        }
    }

    public List<User> findAllBySchoolId(Long id) {
        logger.info("[INFO] Fetching all Users by school id: {}", id);

        List<User> users = userRepository.findAllBySchoolId(id);
        logger.debug("[DEBUG] Fetched all Users by school id: {}", id);

        return users;
    }

    public List<User> findAllByRole(Role role) {
        logger.info("[INFO] Fetching all Users by role: {}", role);

        List<User> users = userRepository.findAllByRole(role);
        logger.debug("[DEBUG] Fetched all Users by role: {}", role);

        return users;
    }
}
