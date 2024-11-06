package com.dbms.dbms_project_backend.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.dbms.dbms_project_backend.event.UserRoleRemovedEvent;
import com.dbms.dbms_project_backend.model.enumerations.Role;

@Component
public class UserRoleRemovedListener implements ApplicationListener<UserRoleRemovedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(UserRoleAddedListener.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @SuppressWarnings("null")
    @Override
    public void onApplicationEvent(UserRoleRemovedEvent event) {
        logger.info("[INFO] User role removed event received: " + event.getUser().getUsername() + " "
                + event.getRole().name());

        Long userId = event.getUser().getId();

        if (event.getRole() == Role.ROLE_STUDENT) {
            String sql = "DELETE FROM student WHERE id = ?";
            jdbcTemplate.update(sql, userId);
        } else if (event.getRole() == Role.ROLE_EMPLOYEE) {
            String removeFromEmployeeSql = "DELETE FROM employee WHERE id = ?";
            jdbcTemplate.update(removeFromEmployeeSql, userId);

            String removeEmployeeRelatedRoles = "DELETE FROM user_roles WHERE user_id = ? AND role_name IN ('ROLE_SCHOOL_INCHARGE', 'ROLE_TT_INCHARGE')";
            jdbcTemplate.update(removeEmployeeRelatedRoles, userId);
        } else if (event.getRole() == Role.ROLE_TEACHER) {
            String sql = "DELETE FROM teacher WHERE id = ?";
            jdbcTemplate.update(sql, userId);
        }
    }
}
