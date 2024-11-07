package com.dbms.dbms_project_backend.model.enumerations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dbms.dbms_project_backend.exception.NotFoundException;

public enum Day {
  MON, TUE, WED, THU, FRI, SAT, SUN;

  private static final Logger logger = LoggerFactory.getLogger(Day.class);

  public static Day fromString(String dayStr) {
    try {
      return Day.valueOf(dayStr);
    } catch (Exception e) {
      logger.warn("[WARN] Day {} not found", dayStr);
      throw new NotFoundException("Day", "name", dayStr);
    }
  }
}
