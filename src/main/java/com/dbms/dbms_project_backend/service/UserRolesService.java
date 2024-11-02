package com.dbms.dbms_project_backend.service;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRepository;
import com.dbms.dbms_project_backend.repository.UserRolesRepository;
import com.dbms.dbms_project_backend.exception.role.RoleNotFoundException;
import com.dbms.dbms_project_backend.exception.user.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesService.class);

    public User addRoleToUser(Long userId, String roleStr) {
        logger.debug("[DEBUG] Attempting to add role {} to user with ID {}", roleStr, userId);

        User user = userService.findById(userId);
        Role role = Role.fromString(roleStr);

        user = userRolesRepository.addRoleByUser(user, role);
        logger.info("[INFO] Role {} added to user with ID {}", role, userId);
        return user;
    }

    public User setRolesForUser(Long userId, Set<String> rolesStr) {
        logger.debug("[DEBUG] Setting roles {} for user with ID {}", rolesStr, userId);
        User user = userService.findById(userId);

        Set<Role> newRoles = rolesStr.stream()
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        Set<Role> existingRoles = userRolesRepository.getRolesByUser(user);

        for (Role role : newRoles) {
            if (!existingRoles.contains(role)) {
                user = userRolesRepository.addRoleByUser(user, role);
                logger.info("[INFO] Role {} added to user with ID {}", role, userId);
            }
        }

        return user;
    }

    public Set<Role> getRolesForUser(Long userId) {
        logger.debug("[DEBUG] Fetching roles for user with ID {}", userId);
        User user = userService.findById(userId);

        Set<Role> roles = userRolesRepository.getRolesByUser(user);
        logger.info("[INFO] Roles for user with ID {}: {}", userId, roles);
        return roles;
    }

    public User deleteRoleFromUser(Long userId, String roleStr) {
        logger.debug("[DEBUG] Attempting to delete role {} from user with ID {}", roleStr, userId);
        User user = userService.findById(userId);

        Role role = Role.fromString(roleStr);

        user = userRolesRepository.deleteRoleByUser(user, role);
        logger.info("[INFO] Role {} deleted from user with ID {}", role, userId);
        return user;
    }
}
