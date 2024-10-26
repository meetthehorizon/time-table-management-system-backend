package com.dbms.dbms_project_backend.dao;

import java.util.Set;
import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.event.user_roles.UserRoleAddedEvent;
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
        try {
            jdbcTemplate.update(sql, user.getId(), role.name());
            user = user.addRole(role);
        } catch (DataIntegrityViolationException e) {
            logger.warn("[WARN] {}", e.getMessage());
        }

        UserRoleAddedEvent event = new UserRoleAddedEvent(this, user, role);
        eventPublisher.publishEvent(event);

        return user;
    }

    @Override
    @Transactional
    public User setRolesByUser(User user) {
        String sql = "INSERT INTO user_roles (user_id, role_name) VALUES (?, ?) ON DUPLICATE KEY UPDATE role_name = role_name";
        List<Object[]> batchArgs = new ArrayList<>();

        for (Role role : user.getRoles()) {
            batchArgs.add(new Object[] { user.getId(), role.name() });
        }

        jdbcTemplate.batchUpdate(sql, batchArgs);
        return user;
    }

    @Override
    public Set<Role> getRolesByUser(User user) {
        String sql = "SELECT role_name FROM user_roles WHERE user_id = ?";

        try {
            List<Role> roles = jdbcTemplate.query(sql, (rs, rowNum) -> Role.valueOf(rs.getString("role_name")),
                    user.getId());
            return Set.copyOf(roles);
        } catch (EmptyResultDataAccessException e) {
            logger.warn("[WARN] No roles found for user");
            return Set.of();
        }
    }

    @Override
    @Transactional
    public User deleteRoleByUser(User user, Role role) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_name = ?";
        try {
            jdbcTemplate.update(sql, user.getId(), role.name());
            user = user.deleteRole(role);
        } catch (EmptyResultDataAccessException e) {
            logger.error("[ERROR]: {}", e.getMessage());
        }
        return user;
    }
}
