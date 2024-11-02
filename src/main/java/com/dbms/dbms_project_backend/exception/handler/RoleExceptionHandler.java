package com.dbms.dbms_project_backend.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.dbms_project_backend.exception.role.RoleNotFoundException;

@Order(2)
@RestControllerAdvice
public class RoleExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(RoleExceptionHandler.class);
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleRoleNotFoundException(RoleNotFoundException exception) {
        logger.warn("[WARN] RoleNotFoundException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        errorDetail.setProperty("description", "The role was not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }
}
