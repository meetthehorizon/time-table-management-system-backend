package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

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

import com.dbms.dbms_project_backend.dto.AddSubjectDto;
import com.dbms.dbms_project_backend.dto.UpdateSubjectDto;
import com.dbms.dbms_project_backend.model.Subject;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SubjectService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
@RequestMapping("/sub")
@RestController
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @Autowired
    private LogService logService;

    @GetMapping()
    public ResponseEntity<List<Subject>> findAll() {
        logService.logRequestAndUser("/subject", "GET");

        List<Subject> subjects = subjectService.findAll();
        return ResponseEntity.ok(subjects);
    }

    @PostMapping
    public ResponseEntity<Subject> addSubject(@Valid @RequestBody AddSubjectDto addsubjectDto) {
        logService.logRequestAndUser("subject", "POST");

        Subject subject = new Subject();
        subject.setCode(addsubjectDto.getCode()).setName(addsubjectDto.getName());
        subject = subjectService.createSubject(subject);
        return ResponseEntity.ok(subject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subject> updateSubject(@PathVariable Long id,
            @Valid @RequestBody UpdateSubjectDto updateSubjectDto) {
        logService.logRequestAndUser("/subject/{id}", "PUT");

        Subject existingSubject = subjectService.findById(id);

        Optional.ofNullable(updateSubjectDto.getName()).ifPresent(existingSubject::setName);
        Optional.ofNullable(updateSubjectDto.getCode()).ifPresent(existingSubject::setCode);

        subjectService.update(existingSubject);
        return ResponseEntity.ok(existingSubject);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subject> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/subject/{id}", "GET");

        Subject subject = subjectService.findById(id);
        return ResponseEntity.ok(subject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
        logService.logRequestAndUser("/subject/{id}", "DELETE");
        subjectService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
