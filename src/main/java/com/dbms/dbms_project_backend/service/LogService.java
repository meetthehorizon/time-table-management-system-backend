package com.dbms.dbms_project_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.model.User;

@Service
public class LogService {
    private static final Logger logger = LoggerFactory.getLogger(LogService.class);

    public void logRequestAndUser(String url, String method) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        logger.debug("[DEBUG] Request by User [" + currentUser + "] on url [" + url + "] with method [" + method + "]");
    }
}
