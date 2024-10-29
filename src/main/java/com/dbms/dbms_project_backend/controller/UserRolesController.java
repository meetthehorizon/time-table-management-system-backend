package com.dbms.dbms_project_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.service.UserRolesService;

@RestController
@RequestMapping("/user-roles")
public class UserRolesController {
    @Autowired
    private UserRolesService userRolesService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("add/{userId}/{roleName}")
    public ResponseEntity<User> addRole(@PathVariable Long userId, @PathVariable String roleName) {
        User user = userRolesService.addRole(userId, roleName);

        return ResponseEntity.ok(user);
    }

}
