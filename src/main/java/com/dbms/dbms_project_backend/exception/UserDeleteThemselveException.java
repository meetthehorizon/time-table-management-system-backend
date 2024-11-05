package com.dbms.dbms_project_backend.exception;

public class UserDeleteThemselveException extends RuntimeException {
    public UserDeleteThemselveException(Long id) {
        super("User [" + id + "] cannot delete themselves");
    }
}
