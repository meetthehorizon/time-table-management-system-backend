package com.dbms.dbms_project_backend.exception.role;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String role) {
        super("Role " + role + " not found");
    }
}
