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

import com.dbms.dbms_project_backend.dto.AddSubjectReqDto;
import com.dbms.dbms_project_backend.dto.UpdateSubjectReqDto;
import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SubjectReqService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subject-req")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER') or hasRole('ROLE_SCHOOL_INCHARGE')")
public class SubjectReqController {
    @Autowired
    private LogService logService;

    @Autowired
    private SubjectReqService subjectReqService;

    @GetMapping
    public ResponseEntity<List<SubjectReq>> getSubjectReqs() {
        logService.logRequestAndUser("/subject-req", "GET");

        List<SubjectReq> subjectReqs = subjectReqService.findAll();
        return ResponseEntity.ok(subjectReqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectReq> getSubjectReq(@PathVariable Long id) {
        logService.logRequestAndUser("/subject-req/{id}", "GET");

        SubjectReq subjectReq = subjectReqService.findById(id);
        return ResponseEntity.ok(subjectReq);
    }

    @PostMapping
    public ResponseEntity<SubjectReq> createSubjectReq(@Valid @RequestBody AddSubjectReqDto addSubjectReq) {
        logService.logRequestAndUser("/subject-req", "POST");

        SubjectReq subjectReq = new SubjectReq();
        subjectReq.setSubjectId(addSubjectReq.getSubjectId()).setNumLab(addSubjectReq.getNumLab())
                .setClassLevel(addSubjectReq.getClassLevel()).setNumLecture(addSubjectReq.getNumLecture())
                .setAttendanceCriteria(addSubjectReq.getAttendanceCriteria())
                .setTeacherPosition(Position.fromString(addSubjectReq.getTeacherPosition()));

        subjectReq = subjectReqService.save(subjectReq);
        return ResponseEntity.ok(subjectReq);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectReq> updateSubjectReq(@PathVariable Long id,
            @Valid @RequestBody UpdateSubjectReqDto updateSubjectReqDto) {
        logService.logRequestAndUser("/subject-req/{id}", "PUT");
        SubjectReq existingSubjectReq = subjectReqService.findById(id);

        Optional.ofNullable(updateSubjectReqDto.getSubjectId()).ifPresent(existingSubjectReq::setSubjectId);
        Optional.ofNullable(updateSubjectReqDto.getNumLab()).ifPresent(existingSubjectReq::setNumLab);
        Optional.ofNullable(updateSubjectReqDto.getClassLevel()).ifPresent(existingSubjectReq::setClassLevel);
        Optional.ofNullable(updateSubjectReqDto.getNumLecture()).ifPresent(existingSubjectReq::setNumLecture);
        Optional.ofNullable(updateSubjectReqDto.getAttendanceCriteria())
                .ifPresent(existingSubjectReq::setAttendanceCriteria);
        Optional.ofNullable(updateSubjectReqDto.getTeacherPosition()).ifPresent(
                teacherPosition -> existingSubjectReq.setTeacherPosition(Position.fromString(teacherPosition)));

        SubjectReq updatedSubjectReq = subjectReqService.update(existingSubjectReq);
        return ResponseEntity.ok(updatedSubjectReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubjectReq(@PathVariable Long id) {
        logService.logRequestAndUser("/subject-req/{id}", "DELETE");

        subjectReqService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
