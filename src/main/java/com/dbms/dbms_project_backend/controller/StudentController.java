package com.dbms.dbms_project_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dbms.dbms_project_backend.model.Student;
import com.dbms.dbms_project_backend.service.StudentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

  
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((Student) authentication.getPrincipal()).getUserId();

        if (!currentUserId.equals(id)) {
            logger.warn("[WARN] Student with id: {} attempted to access another student's data", currentUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }

        logger.info("[INFO] Fetching student with id: {}", id);
        Optional<Student> studentOpt = studentService.findById(id);
        if (studentOpt.isPresent()) {
            return ResponseEntity.ok(studentOpt.get());
        } else {
            logger.warn("[WARN] Student with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_STUDENT')")
    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student updatedStudent) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((Student) authentication.getPrincipal()).getUserId();

        if (!currentUserId.equals(id)) {
            logger.warn("[WARN] Student with id: {} attempted to edit another student's data", currentUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }

        Optional<Student> existingStudentOpt = studentService.findById(id);
        if (!existingStudentOpt.isPresent()) {
            logger.warn("[WARN] Student with id: {} not found for editing", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("[INFO] Updating student with id: {}", id);
        Student existingStudent = existingStudentOpt.get();
        existingStudent.setParentId(updatedStudent.getParentId());

        studentService.updateStudent(existingStudent);
        return ResponseEntity.ok(existingStudent);
    }
}
