package com.dbms.dbms_project_backend.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dbms.dbms_project_backend.event.user_roles.UserRoleAddedEvent;
import com.dbms.dbms_project_backend.model.enumerations.Role;

import jakarta.transaction.Transactional;

@Component
public class UserRoleListener {
    private final static Logger logger = LoggerFactory.getLogger(UserRoleListener.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @EventListener
    @Transactional
    public void handleUserRoleAddedEvent(UserRoleAddedEvent event) {
        logger.info("[INFO] User role added event received: " + event.getUser().getUsername() + " "
                + event.getRole().name());
        if (event.getRole() == Role.ROLE_STUDENT) {
            Long userId = event.getUser().getId();
            String sql = "INSERT INTO student (user_id) VALUES (?)";

            jdbcTemplate.update(sql, userId);
        }
    }
}
