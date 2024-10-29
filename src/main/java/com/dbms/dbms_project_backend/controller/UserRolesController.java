package com.dbms.dbms_project_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.dbms.dbms_project_backend.dto.userRoles.AddRoleDto;
import com.dbms.dbms_project_backend.dto.userRoles.DeleteUserDto;
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
    @PostMapping()
    public ResponseEntity<User> addRole(@RequestBody AddRoleDto addRoleDto) {
        logger.info("[INFO] Adding role '{}' to user with ID {}", addRoleDto.getRoleName(), addRoleDto.getId());
        User user = userRolesService.addRoleToUser(addRoleDto.getId(), addRoleDto.getRoleName());
        logger.debug("[DEBUG] Role '{}' added to user with ID {}", addRoleDto.getRoleName(), addRoleDto.getId());
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    @GetMapping("/{userId}")
    public ResponseEntity<Set<Role>> getRoles(@PathVariable Long userId) {
        logger.info("[INFO] Fetching roles for user with ID {}", userId);
        Set<Role> roles = userRolesService.getRolesForUser(userId);
        logger.debug("[DEBUG] Roles fetched for user with ID {}: {}", userId, roles);
        return ResponseEntity.ok(roles);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping()
    public ResponseEntity<User> deleteRole(@RequestBody DeleteUserDto deleteUserDto) {
        logger.warn("[WARN] Deleting role '{}' from user with ID {}", deleteUserDto.getRoleName(),
                deleteUserDto.getId());
        User user = userRolesService.deleteRoleFromUser(deleteUserDto.getId(), deleteUserDto.getRoleName());
        logger.debug("[DEBUG] Role '{}' deleted from user with ID {}", deleteUserDto.getRoleName(),
                deleteUserDto.getId());
        return ResponseEntity.ok(user);
    }

}
