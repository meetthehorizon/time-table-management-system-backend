package com.dbms.dbms_project_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.service.UserRolesService;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/user-roles")
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesController.class);

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add/{userId}/{roleName}")
    public ResponseEntity<User> addRole(@PathVariable Long userId, @PathVariable String roleName) {
        logger.info("[INFO] Adding role '{}' to user with ID {}", roleName, userId);
        User user = userRolesService.addRoleToUser(userId, roleName);
        logger.debug("[DEBUG] Role '{}' added to user with ID {}", roleName, userId);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/set/{userId}")
    public ResponseEntity<User> setRoles(@PathVariable Long userId, @RequestBody Set<String> roles) {
        logger.info("[INFO] Setting roles for user with ID {}", userId);
        User user = userRolesService.setRolesForUser(userId, roles);
        logger.debug("[INFO] Roles set for user with ID {}: {}", userId, roles);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @GetMapping("/get/{userId}")
    public ResponseEntity<Set<Role>> getRoles(@PathVariable Long userId) {
        logger.info("[INFO] Fetching roles for user with ID {}", userId);
        Set<Role> roles = userRolesService.getRolesForUser(userId);
        logger.debug("[DEBUG] Roles fetched for user with ID {}: {}", userId, roles);
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{userId}/{roleName}")
    public ResponseEntity<User> deleteRole(@PathVariable Long userId, @PathVariable String roleName) {
        logger.warn("[WARN] Deleting role '{}' from user with ID {}", roleName, userId);
        User user = userRolesService.deleteRoleFromUser(userId, roleName);
        logger.debug("[DEBUG] Role '{}' deleted from user with ID {}", roleName, userId);
        return ResponseEntity.ok(user);
    }
}
