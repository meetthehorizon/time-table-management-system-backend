package com.dbms.dbms_project_backend.controller;

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

import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SubjectReqService;

@RestController
@RequestMapping("/subject-req")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER') or hasRole('ROLE_SCHOOL_INCHARGE')")
public class SubjectReqController {
    @Autowired
    private LogService logService;

    @Autowired
    private SubjectReqService subjectReqService;

    @GetMapping("/{id}")
    public ResponseEntity<SubjectReq> getSubjectReq(@PathVariable Long id) {
        logService.logRequestAndUser("/subject-req/{id}", "GET");

        SubjectReq subjectReq = subjectReqService.findById(id);
        return ResponseEntity.ok(subjectReq);
    }

    @PostMapping
    public ResponseEntity<SubjectReq> createSubjectReq(@RequestBody SubjectReq subjectReq) {
        logService.logRequestAndUser("/subject-req", "POST");

        SubjectReq newSubjectReq = subjectReqService.save(subjectReq);
        return ResponseEntity.ok(newSubjectReq);
    }

    @PutMapping
    public ResponseEntity<SubjectReq> updateSubjectReq(@RequestBody SubjectReq subjectReq) {
        logService.logRequestAndUser("/subject-req", "PUT");

        SubjectReq updatedSubjectReq = subjectReqService.update(subjectReq);
        return ResponseEntity.ok(updatedSubjectReq);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSubjectReq(@RequestBody SubjectReq subjectReq) {
        logService.logRequestAndUser("/subject-req", "DELETE");

        subjectReqService.delete(subjectReq);
        return ResponseEntity.ok("Subject requirement deleted successfully");
    }
}
