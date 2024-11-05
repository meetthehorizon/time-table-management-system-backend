package com.dbms.dbms_project_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.subject.SubjectDto;
import com.dbms.dbms_project_backend.model.Subject;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SubjectService;

@RequestMapping("/subject")
@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private LogService logService;


    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @GetMapping()
    public ResponseEntity<List<Subject>> findAll() {
        logService.logRequestAndUser("/subject", "GET");

        List<Subject> subjects = subjectService.findAll();
        return ResponseEntity.ok(subjects);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @PostMapping
    public ResponseEntity<Subject> addSubject(@RequestBody SubjectDto addsubjectDto) {
        logService.logRequestAndUser("subject", "POST");

        Subject subject = new Subject();
        subject.setId(addsubjectDto.getId()).setCode(addsubjectDto.getCode()).setName(addsubjectDto.getName());
        subject = subjectService.createSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id, @RequestBody SubjectDto updateSubjectDto) {
        logService.logRequestAndUser("/subject/{id}", "PUT");

         Subject existingSubject = subjectService.findById(id);
         existingSubject.setName(updateSubjectDto.getName()).setCode(updateSubjectDto.getCode());
         subjectService.updatedSubject(existingSubject);
         return ResponseEntity.ok(existingSubject);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @GetMapping("/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/subject/{id}", "GET");

        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(subject);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        logService.logRequestAndUser("/subject/{id}", "DELETE");
        subjectService.deleteById(id);
        return ResponseEntity.noContent().build();

        
    }

    
}
