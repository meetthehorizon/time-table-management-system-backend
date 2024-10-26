package com.dbms.dbms_project_backend.exception;

import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(1)
@RestControllerAdvice
public class SqlExceptionHandler {
    @ExceptionHandler(DataAccessException.class)
    public ProblemDetail handleDataAccessException(DataAccessException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),
                exception.getMessage());
        errorDetail.setProperty("description", "Database access error occurred");
        return errorDetail;
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ProblemDetail handleDuplicateKeyException(DuplicateKeyException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(409),
                exception.getMessage());
        errorDetail.setProperty("description", "Duplicate key error");
        return errorDetail;
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ProblemDetail handleEmptyResultDataAccessException(EmptyResultDataAccessException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(404),
                exception.getMessage());
        errorDetail.setProperty("description", "No results found for the query");
        return errorDetail;
    }

    @ExceptionHandler(QueryTimeoutException.class)
    public ProblemDetail handleQueryTimeoutException(QueryTimeoutException exception) {
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(504),
                exception.getMessage());
        errorDetail.setProperty("description", "Database query timed out");
        return errorDetail;
    }
}
