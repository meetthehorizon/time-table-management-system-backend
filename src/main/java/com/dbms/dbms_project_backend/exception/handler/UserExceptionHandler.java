package com.dbms.dbms_project_backend.exception.handler;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.dbms_project_backend.exception.UserDeleteThemselveException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(2)
@RestControllerAdvice
public class UserExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ExceptionHandler(UserDeleteThemselveException.class)
    public ResponseEntity<ProblemDetail> handleUserDeleteThemselveException(UserDeleteThemselveException exception) {
        logger.warn("[WARN] UserDeleteThemselveException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                exception.getMessage());
        errorDetail.setProperty("description", "The user cannot delete themselves");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }
}
