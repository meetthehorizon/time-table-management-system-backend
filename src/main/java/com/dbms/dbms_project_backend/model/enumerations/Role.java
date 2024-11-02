package com.dbms.dbms_project_backend.model.enumerations;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.dbms.dbms_project_backend.exception.role.RoleNotFoundException;

public enum Role {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_PARENT,
    ROLE_TT_INCHARGE,
    ROLE_SCHOOL_INCHARGE,
    ROLE_GENERAL_MANAGER,
    ROLE_STUDENT,
    ROLE_TEACHER,
    ROLE_EMPLOYEE;

    private static final Logger logger = LoggerFactory.getLogger(Role.class);

    public static Role fromString(String role) {
        try {
            return Role.valueOf(role);
        } catch (RoleNotFoundException e) {
            logger.warn("[WARN] Role {} not found", role);
            throw new RoleNotFoundException(role);
        }
    }
}
