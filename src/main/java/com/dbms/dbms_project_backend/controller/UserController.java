package com.dbms.dbms_project_backend.controller;

import java.util.List;

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

import com.dbms.dbms_project_backend.exception.UserDeleteThemselveException;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestMapping("/users")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> allUsers() {
        logService.logRequestAndUser("/users", "GET");

        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and authentication.principal.id == #id)")
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        logService.logRequestAndUser("/users/{id}", "GET");

        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user) {
        logService.logRequestAndUser("users", "POST");

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or (hasRole('ROLE_USER') and authentication.principal.id == #id)")
    @PutMapping("/{id}")
    public ResponseEntity<User> editUser(@PathVariable Long id, @RequestBody User updatedUser) {
        logService.logRequestAndUser("/users/{id}", "PUT");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (!currentUser.getRoles().contains(Role.ROLE_ADMIN) && !currentUser.getId().equals(id)) {
            logger.warn("[WARN] User with id: {} attempted to edit user with id: {}", currentUser.getId(), id);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        User existingUser = userService.findById(id);

        existingUser.setName(updatedUser.getName());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhone(updatedUser.getPhone());

        userService.updateUser(existingUser);
        return ResponseEntity.ok(existingUser);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logService.logRequestAndUser("/users/{id}", "DELETE");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        User existingUser = userService.findById(id);

        if (existingUser.getId().equals(currentUser.getId())) {
            logger.warn("[WARN] User with id: {} attempted to delete themselves", currentUser.getId());
            throw new UserDeleteThemselveException(id);
        }

        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
