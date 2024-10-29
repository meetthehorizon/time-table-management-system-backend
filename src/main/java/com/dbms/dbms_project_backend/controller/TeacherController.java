package com.dbms.dbms_project_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.service.TeacherService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((Teacher) authentication.getPrincipal()).getId(); // Assuming getId() method exists

        // Check if the current user is trying to access their own information
        if (!currentUserId.equals(id)) {
            logger.warn("[WARN] Teacher with id: {} attempted to access another teacher's data", currentUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }

        logger.info("[INFO] Fetching teacher with id: {}", id);
        Optional<Teacher> teacherOpt = teacherService.findById(id);
        if (teacherOpt.isPresent()) {
            return ResponseEntity.ok(teacherOpt.get());
        } else {
            logger.warn("[WARN] Teacher with id: {} not found", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PreAuthorize("hasRole('ROLE_TEACHER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id, @RequestBody Teacher updatedTeacher) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long currentUserId = ((Teacher) authentication.getPrincipal()).getId(); // Assuming getId() method exists

        // Check if the current user is trying to edit their own information
        if (!currentUserId.equals(id)) {
            logger.warn("[WARN] Teacher with id: {} attempted to edit another teacher's data", currentUserId);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden
        }

        Optional<Teacher> existingTeacherOpt = teacherService.findById(id);
        if (!existingTeacherOpt.isPresent()) {
            logger.warn("[WARN] Teacher with id: {} not found for editing", id);
            return ResponseEntity.notFound().build();
        }

        logger.info("[INFO] Updating teacher with id: {}", id);
        Teacher existingTeacher = existingTeacherOpt.get();
        existingTeacher.setSubjectId(updatedTeacher.getSubjectId());
        existingTeacher.setPositions(updatedTeacher.getPositions());

        teacherService.updateTeacher(existingTeacher);
        return ResponseEntity.ok(existingTeacher);
    }
}
