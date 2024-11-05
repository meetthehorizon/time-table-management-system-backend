package com.dbms.dbms_project_backend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.school.AddSchoolDto;
import com.dbms.dbms_project_backend.model.School;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SchoolService;

@RequestMapping("/school")
@RestController
public class SchoolController {
    @Autowired
    private SchoolService schoolService;

    @Autowired
    private LogService logService;

    private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @GetMapping()
    public ResponseEntity<List<School>> findAll() {
        logService.logRequestAndUser("/school", "GET");

        List<School> schools = schoolService.findAll();
        return ResponseEntity.ok(schools);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<School> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/school/{id}", "GET");

        School school = schoolService.findById(id);
        return ResponseEntity.ok(school);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @PostMapping()
    public ResponseEntity<School> addSchool(@RequestBody AddSchoolDto addSchoolDto) {
        logService.logRequestAndUser("/school", "POST");

        School school = new School();
        school.setName(addSchoolDto.getName()).setAddress(addSchoolDto.getAddress()).setPhone(addSchoolDto.getPhone())
                .setEmail(addSchoolDto.getEmail());

        school = schoolService.addSchool(school);
        return ResponseEntity.ok(school);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<School> editSchool(@PathVariable Long id, @RequestBody School updatedSchool) {
        logService.logRequestAndUser("/school/{id}", "POST");

        School existingSchool = schoolService.findById(id);
        logger.info("[INFO] Editing School with schoolId: {}", id);
        existingSchool.setName(updatedSchool.getName()).setAddress(updatedSchool.getAddress())
                .setEmail(updatedSchool.getEmail()).setPhone(updatedSchool.getPhone());

        schoolService.addSchool(existingSchool);
        return ResponseEntity.ok(existingSchool);
    }
}
