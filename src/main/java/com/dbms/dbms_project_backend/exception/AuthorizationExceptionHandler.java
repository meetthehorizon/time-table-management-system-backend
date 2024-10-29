package com.dbms.dbms_project_backend.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

@Order(1)
@RestControllerAdvice
public class AuthorizationExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDeniedException(AccessDeniedException exception) {
        logger.warn("[WARN] Access denied: {}", exception.getMessage());
        logger.debug("[DEBUG] AccessDeniedException stack trace: ", exception);

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),
                exception.getMessage());
        errorDetail.setProperty("description", "You are not authorized to access this resource.");
        
        logger.info("[INFO] Returning error detail: {}", errorDetail);
        return errorDetail;
    }
}
