package com.dbms.dbms_project_backend.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(1)
@RestControllerAdvice
public class AuthenticationExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

    @ExceptionHandler({ BadCredentialsException.class, AccountStatusException.class })
    public ProblemDetail handleAuthenticationExceptions(Exception exception) {
        ProblemDetail errorDetail;

        if (exception instanceof BadCredentialsException) {
            logger.warn("[WARN] BadCredentialsException: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401), exception.getMessage());
            errorDetail.setProperty("description", "The username or password is incorrect.");
        } else {
            logger.warn("[WARN] AccountStatusException: {}", exception.getMessage());
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403), exception.getMessage());
            errorDetail.setProperty("description", "The account is locked.");
        }

        return errorDetail;
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ProblemDetail handleInsufficientAuthenticationException(InsufficientAuthenticationException exception) {
        logger.warn("[WARN] InsufficientAuthenticationException: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
                exception.getMessage());
        errorDetail.setProperty("description", "Insufficient authentication");
        return errorDetail;
    }
}
