package com.dbms.dbms_project_backend.dao;

import java.util.Set;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.event.UserRoleAddedEvent;
import com.dbms.dbms_project_backend.event.UserRoleRemovedEvent;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRolesRepository;

import jakarta.transaction.Transactional;

@Repository
public class UserRolesDao implements UserRolesRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final Logger logger = LoggerFactory.getLogger(UserRolesDao.class);

    @Override
    @Transactional
    public User addRoleByUser(User user, Role role) {
        String sql = "INSERT INTO user_roles (user_id, role_name) VALUES (?, ?)";
        Object[] params = { user.getId(), role.name() };
        logger.debug("[DEBUG] Adding role {} to user {}", role, user.getId());
        jdbcTemplate.update(sql, params);

        logger.info("[INFO] Role {} added to user {}", role, user.getId());

        logger.debug("[DEBUG] Published UserRoleAddedEvent for user {} and role {}", user.getId(), role);
        UserRoleAddedEvent userRoleAddedEvent = new UserRoleAddedEvent(this, user, role);
        eventPublisher.publishEvent(userRoleAddedEvent);

        return user.addRole(role);
    }

    @Override
    public Set<Role> getRolesByUser(User user) {
        String sql = "SELECT role_name FROM user_roles WHERE user_id = ?";
        logger.debug("[DEBUG] Fetching roles for user {}", user.getId());
        List<Role> roles = jdbcTemplate.query(sql, (rs, rowNum) -> Role.valueOf(rs.getString("role_name")),
                user.getId());
        logger.info("[INFO] Roles fetched for user {}: {}", user.getId(), roles);
        return Set.copyOf(roles);
    }

    @Override
    @Transactional
    public User deleteRoleByUser(User user, Role role) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_name = ?";
        logger.debug("[DEBUG] Deleting role {} from user {}", role, user.getId());
        jdbcTemplate.update(sql, user.getId(), role.name());
        user = user.deleteRole(role);
        logger.info("[INFO] Role {} deleted from user {}", role, user.getId());

        logger.debug("[DEBUG] Published UserRoleRemovedEvent for user {} and role {}", user.getId(), role);
        UserRoleRemovedEvent userRoleRemovedEvent = new UserRoleRemovedEvent(this, user, role);
        eventPublisher.publishEvent(userRoleRemovedEvent);

        return user;
    }
}
