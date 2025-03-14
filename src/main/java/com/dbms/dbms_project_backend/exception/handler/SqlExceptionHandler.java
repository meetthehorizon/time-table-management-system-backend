package com.dbms.dbms_project_backend.exception.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;

@Order(1)
@RestControllerAdvice
public class SqlExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(SqlExceptionHandler.class);

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ProblemDetail> handleDataAccessException(DataAccessException exception) {
        logger.error("[ERROR] DataAccessException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "A database error occurred while processing the request");

        errorDetail.setProperty("description", exception.getMessage());
        errorDetail.setProperty("errorType", exception.getClass().getSimpleName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(NotFoundException exception) {
        logger.warn("[WARN] UserNotFoundException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                exception.getMessage());
        errorDetail.setProperty("description", "The requested resource was not found");

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @ExceptionHandler(FieldValueAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleFieldValueAlreadyExistsException(
            FieldValueAlreadyExistsException exception) {
        logger.warn("[WARN] UserDetailAlreadyExistsException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetail);
    }
}
