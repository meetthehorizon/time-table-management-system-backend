package com.dbms.dbms_project_backend.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Order(2)
@RestControllerAdvice
public class BaseExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception exception) {
        logger.warn("[WARN] Handling generic exception: {}", exception.getMessage());
        logger.debug("[DEBUG] Exception details: ", exception);

        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());
        errorDetail.setProperty("description", "An unexpected error occurred.");

        logger.info("[INFO] Returning error detail with status: {}", HttpStatus.INTERNAL_SERVER_ERROR);
        return errorDetail;
    }
}
