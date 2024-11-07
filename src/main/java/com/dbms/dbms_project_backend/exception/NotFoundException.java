package com.dbms.dbms_project_backend.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String object, String field, Long valueLong) {
        super(String.format("%s [%s: %d] not found", object, field, valueLong));
    }

    public NotFoundException(String object, String field, String valueString) {
        super(String.format("%s [%s: %s] not found", object, field, valueString));
    }
}
