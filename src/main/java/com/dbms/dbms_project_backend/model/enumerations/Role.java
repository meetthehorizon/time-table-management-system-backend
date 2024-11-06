package com.dbms.dbms_project_backend.model.enumerations;

import org.slf4j.LoggerFactory;

import com.dbms.dbms_project_backend.exception.NotFoundException;

import org.slf4j.Logger;

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

    public static Role fromString(String roleStr) {
        try {
            return Role.valueOf(roleStr);
        } catch (Exception e) {
            logger.warn("[WARN] Role {} not found", roleStr);
            throw new NotFoundException("Role", "roleName", roleStr);
        }
    }
}
