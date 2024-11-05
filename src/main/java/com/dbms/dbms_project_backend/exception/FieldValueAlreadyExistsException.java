package com.dbms.dbms_project_backend.exception;

public class FieldValueAlreadyExistsException extends RuntimeException {
    public FieldValueAlreadyExistsException(String object, String field, String value) {
        super(String.format("%s with %s %s already exists", object, field, value));
    }
}
