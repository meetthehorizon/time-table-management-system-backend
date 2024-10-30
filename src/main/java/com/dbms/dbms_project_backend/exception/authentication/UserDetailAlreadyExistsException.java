package com.dbms.dbms_project_backend.exception.authentication;

public class UserDetailAlreadyExistsException extends RuntimeException {
    public UserDetailAlreadyExistsException(String field, String value) {
        super(String.format("User with %s %s already exists", field, value));        
    }
}
