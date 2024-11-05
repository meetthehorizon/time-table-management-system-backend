package com.dbms.dbms_project_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.TeacherService;

@RequestMapping("/teachers")
@RestController
public class TeacherController {
    @Autowired
    private TeacherService teacherService;

    @Autowired
    private LogService logService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    public ResponseEntity<List<Teacher>> allTeachers() {
        logService.logRequestAndUser("/teachers", "GET");

        List<Teacher> teachers = teacherService.findAll();
        return ResponseEntity.ok(teachers);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    public ResponseEntity<Teacher> getTeacherById(Long id) {
        logService.logRequestAndUser("/teachers/{id}", "GET");

        Teacher teacher = teacherService.findById(id);
        return ResponseEntity.ok(teacher);
    }


}
