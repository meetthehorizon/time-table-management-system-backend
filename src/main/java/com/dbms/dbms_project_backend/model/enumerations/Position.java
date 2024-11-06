package com.dbms.dbms_project_backend.model.enumerations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbms.dbms_project_backend.exception.NotFoundException;

public enum Position {
    PRT,
    TGT,
    PGT;

    private static final Logger logger = LoggerFactory.getLogger(Position.class);

    public static Position fromString(String positionStr) {
        try {
            return Position.valueOf(positionStr);
        } catch (Exception e) {
            logger.warn("[WARN] Position {} not found", positionStr);
            throw new NotFoundException("Position", "name", positionStr);
        }
    }
}
