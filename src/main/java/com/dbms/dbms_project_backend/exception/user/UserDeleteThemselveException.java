package com.dbms.dbms_project_backend.exception.user;

public class UserDeleteThemselveException extends RuntimeException {
    public UserDeleteThemselveException(Long id) {
        super("User with ID " + id + " cannot delete themselves");
    }
}
