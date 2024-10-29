package com.dbms.dbms_project_backend.service;

import com.dbms.dbms_project_backend.dao.UserDao;
import com.dbms.dbms_project_backend.dao.UserRolesDao;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
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
    private UserRolesDao userRolesDao;

    @Autowired
    private UserDao userDao;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesService.class);

    public User addRoleToUser(Long userId, String roleStr) {
        logger.debug("[DEBUG] Attempting to add role {} to user with ID {}", roleStr, userId);
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            logger.warn("[WARN] User with ID {} not found", userId);
            throw new IllegalArgumentException("User not found");
        }
        User user = userOpt.get();
        Role role = Role.valueOf(roleStr.toUpperCase());

        userRolesDao.addRoleByUser(user, role);
        logger.info("[INFO] Role {} added to user with ID {}", role, userId);
        return user;
    }

    public User setRolesForUser(Long userId, Set<String> rolesStr) {
        logger.debug("[DEBUG] Setting roles {} for user with ID {}", rolesStr, userId);
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            logger.warn("[WARN] User with ID {} not found", userId);
            throw new IllegalArgumentException("User not found");
        }
        User user = userOpt.get();
        Set<Role> newRoles = rolesStr.stream()
                .map(String::toUpperCase)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        Set<Role> existingRoles = userRolesDao.getRolesByUser(user);

        for (Role role : newRoles) {
            if (!existingRoles.contains(role)) {
                userRolesDao.addRoleByUser(user, role);
                logger.info("[INFO] Role {} added to user with ID {}", role, userId);
            }
        }

        return user;
    }

    public Set<Role> getRolesForUser(Long userId) {
        logger.debug("[DEBUG] Fetching roles for user with ID {}", userId);
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            logger.warn("[WARN] User with ID {} not found", userId);
            throw new IllegalArgumentException("User not found");
        }
        Set<Role> roles = userRolesDao.getRolesByUser(userOpt.get());
        logger.info("[INFO] Roles for user with ID {}: {}", userId, roles);
        return roles;
    }

    public User deleteRoleFromUser(Long userId, String roleStr) {
        logger.debug("[DEBUG] Attempting to delete role {} from user with ID {}", roleStr, userId);
        Optional<User> userOpt = userDao.findById(userId);
        if (userOpt.isEmpty()) {
            logger.warn("[WARN] User with ID {} not found", userId);
            throw new IllegalArgumentException("User not found");
        }
        User user = userOpt.get();
        Role role = Role.valueOf(roleStr.toUpperCase());

        userRolesDao.deleteRoleByUser(user, role);
        logger.info("[INFO] Role {} deleted from user with ID {}", role, userId);
        return user;
    }
}
