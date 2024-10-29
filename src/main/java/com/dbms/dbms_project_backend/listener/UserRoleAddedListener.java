package com.dbms.dbms_project_backend.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dbms.dbms_project_backend.event.UserRoleAddedEvent;
import com.dbms.dbms_project_backend.model.enumerations.Role;

import jakarta.transaction.Transactional;

@Component
public class UserRoleAddedListener implements ApplicationListener<UserRoleAddedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(UserRoleAddedListener.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void onApplicationEvent(UserRoleAddedEvent event) {
        logger.info("[INFO] User role added event received: " + event.getUser().getUsername() + " "
                + event.getRole().name());
        Long userId = event.getUser().getId();

        if (event.getRole() == Role.ROLE_STUDENT) {
            String sql = "INSERT INTO student (user_id) VALUES (?)";
            jdbcTemplate.update(sql, userId);
        } else if (event.getRole() == Role.ROLE_EMPLOYEE) {
            String sql = "INSERT INTO employee (user_id) VALUES (?)";
            jdbcTemplate.update(sql, userId);
        } else if (event.getRole() == Role.ROLE_STUDENT) {
            String sql = "INSERT INTO student (user_id) VALUES (?)";
            jdbcTemplate.update(sql, userId);
        }
    }
}
