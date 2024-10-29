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
        Long userId = event.getUser().getId();
        logger.info("[INFO] User role added event received: (" + userId + ") " + event.getUser().getUsername() + " "
                + event.getRole().name());

        if (event.getRole() == Role.ROLE_STUDENT) {
            logger.debug("[DEBUG] Adding student record for user with ID {}", userId);
            String sql = "INSERT INTO student (id) VALUES (?)";
            jdbcTemplate.update(sql, userId);
        } else if (event.getRole() == Role.ROLE_EMPLOYEE) {
            logger.debug("[DEBUG] Adding employee record for user with ID {}", userId);
            try {
                String sql = "INSERT INTO employee (id) VALUES (?)";
                jdbcTemplate.update(sql, userId);
            } catch (Exception e) {
                logger.warn("[WARN] Data integrity violation while adding employee record for user with ID {}: {}",
                        userId,
                        e.getMessage());
            }
        }

        logger.info("[INFO] User role added event processed: (" + userId + ") " + event.getUser().getUsername() + " "
                + event.getRole().name());
    }
}
