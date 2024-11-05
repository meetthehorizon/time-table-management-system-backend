package com.dbms.dbms_project_backend.service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRolesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@Service
public class UserRolesService {
    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesService.class);

    public User addRoleToUser(Long userId, String roleStr) {
        logger.info("[INFO] Adding Role: {} to User with id: {}", roleStr, userId);

        User user = userService.findById(userId);
        Role role = Role.fromString(roleStr);

        user = userRolesRepository.addRoleByUser(user, role);
        logger.debug("[DEBUG] Role: {} added to User with id: {}", roleStr, userId);
        return user;
    }

    public Set<Role> getRolesForUser(Long userId) {
        logger.info("[INFO] Fetching roles for User with id: {}", userId);
        User user = userService.findById(userId);

        Set<Role> roles = userRolesRepository.getRolesByUser(user);
        logger.debug("[DEBUG] Roles for User with id {}: {}", userId, roles);
        return roles;
    }

    public User deleteRoleFromUser(Long userId, String roleStr) {
        logger.info("[INFO] Attempting to delete Role: {} from User with id: {}", roleStr, userId);
        User user = userService.findById(userId);
        Role role = Role.fromString(roleStr);

        if (!user.getRoles().contains(role)) {
            throw new NotFoundException("User", "roleName", roleStr);
        }

        user = userRolesRepository.deleteRoleByUser(user, role);
        logger.debug("[DEBUG] Role: {} deleted from User with id: {}", roleStr, userId);
        return user;
    }
}
