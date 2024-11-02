package com.dbms.dbms_project_backend.exception.user;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long userId) {
        super(String.format("User with id %d not found", userId));
    }
}
