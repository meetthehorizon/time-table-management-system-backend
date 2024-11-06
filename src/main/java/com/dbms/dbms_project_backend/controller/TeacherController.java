package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.teacher.UpdateTeacherDto;
import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.TeacherService;
import com.dbms.dbms_project_backend.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/teachers")
@RestController
public class TeacherController {
        @Autowired
        private TeacherService teacherService;

        @Autowired
        private UserService userService;

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
        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER') or (hasRole('ROLE_TEACHER') and authentication.principal.id == #id)")
        public ResponseEntity<Teacher> getTeacherById(@PathVariable Long id) {
                logService.logRequestAndUser("/teachers/{id}", "GET");

                Teacher teacher = teacherService.findById(id);
                return ResponseEntity.ok(teacher);
        }

        @PutMapping("/{id}")
        @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER') or (hasRole('ROLE_TEACHER') and authentication.principal.id == #id)")
        public ResponseEntity<Teacher> updateTeacher(@PathVariable Long id,
                        @Valid @RequestBody UpdateTeacherDto updateTeacherDto) {
                logService.logRequestAndUser("/teachers/{id}", "PUT");

                if (updateTeacherDto.getUser() != null) {
                        User existingUser = userService.findById(id);

                        Optional.ofNullable(updateTeacherDto.getUser().getName())
                                        .ifPresent(existingUser::setName);

                        Optional.ofNullable(updateTeacherDto.getUser().getEmail())
                                        .ifPresent(existingUser::setEmail);

                        Optional.ofNullable(updateTeacherDto.getUser().getPhone())
                                        .ifPresent(existingUser::setPhone);

                        Optional.ofNullable(updateTeacherDto.getUser().getAddress())
                                        .ifPresent(existingUser::setAddress);

                        userService.updateUser(existingUser);
                }

                Teacher existingTeacher = teacherService.findById(id);
                Optional.ofNullable(updateTeacherDto.getPosition()).map(Position::fromString)
                                .ifPresent(existingTeacher::setPosition);
                Optional.ofNullable(updateTeacherDto.getSubjectId()).ifPresent(existingTeacher::setSubjectId);

                Teacher updatedTeacher = teacherService.update(existingTeacher);
                return ResponseEntity.ok(updatedTeacher);
        }
}
