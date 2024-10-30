package com.dbms.dbms_project_backend.exception.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.dbms.dbms_project_backend.exception.authentication.UserDetailAlreadyExistsException;

@Order(1)
@RestControllerAdvice
public class AuthenticationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationExceptionHandler.class);

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentialException(BadCredentialsException exception) {
        logger.warn("[WARN] BadCredentialsException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
                "The username or password is incorrect");
        errorDetail.setProperty("description", "The username or password is incorrect");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDetail);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleMethodNotSupportedException(
            HttpRequestMethodNotSupportedException exception) {
        logger.warn("[WARN] Handling method not supported exception: {}", exception.getMessage());
        logger.debug("[DEBUG] Exception details: ", exception);

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.METHOD_NOT_ALLOWED,
                exception.getMessage());

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception exception) {
        logger.warn("[WARN] Handling generic exception: {}", exception.getMessage());
        logger.debug("[DEBUG] Exception details: ", exception);

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());
        errorDetail.setProperty("description", "An unexpected error occurred.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
    }

    @ExceptionHandler(UserDetailAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleUserDetailAlreadyExistsException(
            UserDetailAlreadyExistsException exception) {
        logger.warn("[WARN] UserDetailAlreadyExistsException: {}", exception.getMessage());

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, exception.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDetail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // @ExceptionHandler(Exception.class)
    // public ProblemDetail handleSecurityException(Exception exception) {
    // ProblemDetail errorDetail = null;
    // exception.printStackTrace();

    // if (exception instanceof BadCredentialsException) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),
    // exception.getMessage());
    // errorDetail.setProperty("description", "The username or password is
    // incorrect");

    // return errorDetail;
    // }

    // if (exception instanceof AccountStatusException) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
    // exception.getMessage());
    // errorDetail.setProperty("description", "The account is locked");
    // }

    // if (exception instanceof AccessDeniedException) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
    // exception.getMessage());
    // errorDetail.setProperty("description", "You are not authorized to access this
    // resource");
    // }

    // if (exception instanceof SignatureException) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
    // exception.getMessage());
    // errorDetail.setProperty("description", "The JWT signature is invalid");
    // }

    // if (exception instanceof ExpiredJwtException) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
    // exception.getMessage());
    // errorDetail.setProperty("description", "The JWT token has expired");
    // }

    // if (errorDetail == null) {
    // errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500),
    // exception.getMessage());
    // errorDetail.setProperty("description", "Unknown internal server error.");
    // }

    // return errorDetail;
    // }
}
