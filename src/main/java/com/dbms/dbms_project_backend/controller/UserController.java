package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("")
    public ResponseEntity<List<User>> allUsers() {
        logger.info("[INFO] Fetching all users");
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and authentication.principal.id == #id)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logger.info("[INFO] Fetching user with id: {}", id);
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get());
        } else {
            logger.warn("[WARN] User with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logger.info("[INFO] Creating new user with email: {}", user.getEmail());
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and authentication.principal.id == #id)")
    @PutMapping("/edit/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN) && !currentUser.getId().equals(id)) {
            logger.warn("[WARN] User with id: {} attempted to edit user with id: {}", currentUser.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        Optional<User> existingUserOpt = userService.findById(id);
        if (!existingUserOpt.isPresent()) {
            logger.warn("[WARN] User with id: {} not found for editing", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("[INFO] Editing user with id: {}", id);
        User existingUser = existingUserOpt.get();
        existingUser.setName(updatedUser.getName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());

        userService.updateUser(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        Optional<User> existingUserOpt = userService.findById(id);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (existingUser.getId().equals(currentUser.getId())) {
                logger.warn("[WARN] User with id: {} attempted to delete themselves", currentUser.getId());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null); // 403 Forbidden
            }

            logger.info("[INFO] Deleting user with id: {}", id);
            userService.deleteUser(id);
            return ResponseEntity.noContent().build(); // 204 No Content
        } else {
            logger.warn("[WARN] User with id: {} not found for deletion", id);
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}
