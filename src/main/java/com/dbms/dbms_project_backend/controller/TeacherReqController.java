package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.dbms.dbms_project_backend.dto.AddTeacherReqDto;
import com.dbms.dbms_project_backend.dto.UpdateTeacherReqDto;
import com.dbms.dbms_project_backend.model.Employee;
import com.dbms.dbms_project_backend.model.TeacherReq;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.service.EmployeeService;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.TeacherReqService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SCHOOL_INCHARGE')")
@RestController
@RequestMapping("/teacher-req")
public class TeacherReqController {
    @Autowired
    private LogService logService;

    @Autowired
    private TeacherReqService teacherReqService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<List<TeacherReq>> findAll() {
        logService.logRequestAndUser("/teacher-req", "GET");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();

        if (currentUser.getRoles().contains(Role.ROLE_ADMIN)) {
            return ResponseEntity.ok(teacherReqService.findAll());
        } else {
            Employee employee = employeeService.findById(currentUser.getId());
            return ResponseEntity.ok(teacherReqService.findBySchoolId(employee.getSchoolId()));
        }
    }

    @GetMapping("/school/{schoolId}")
    public ResponseEntity<List<TeacherReq>> findBySchoolId(@PathVariable Long schoolId) {
        logService.logRequestAndUser("/teacher-req/school/{id}", "GET");

        List<TeacherReq> teacherReqs = teacherReqService.findBySchoolId(schoolId);
        return ResponseEntity.ok(teacherReqs);
    }

    @GetMapping("{id}")
    public ResponseEntity<TeacherReq> findById(Long id) {
        logService.logRequestAndUser("/teacher-req/{id}", "GET");

        TeacherReq teacherReq = teacherReqService.findById(id);
        return ResponseEntity.ok(teacherReq);
    }

    @PostMapping
    public ResponseEntity<TeacherReq> save(@Valid @RequestBody AddTeacherReqDto addTeacherReqDto) {
        logService.logRequestAndUser("/teacher-req", "POST");

        TeacherReq teacherReq = new TeacherReq();
        teacherReq.setSchoolId(addTeacherReqDto.getSchoolId())
                .setPosition(Position.fromString(addTeacherReqDto.getPosition()))
                .setSubjectId(addTeacherReqDto.getSubjectId()).setTeacherId(addTeacherReqDto.getTeacherId());

        TeacherReq savedTeacherReq = teacherReqService.save(teacherReq);
        return ResponseEntity.ok(savedTeacherReq);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherReq> update(@PathVariable Long id,
            @Valid @RequestBody UpdateTeacherReqDto updateTeacherReqDto) {
        logService.logRequestAndUser("/teacher-req/{id}", "PUT");
        TeacherReq existingTeacherReq = teacherReqService.findById(id);

        Optional.ofNullable(updateTeacherReqDto.getSchoolId()).ifPresent(existingTeacherReq::setSchoolId);
        Optional.ofNullable(updateTeacherReqDto.getPosition()).ifPresent(existingTeacherReq::setPosition);
        Optional.ofNullable(updateTeacherReqDto.getSubjectId()).ifPresent(existingTeacherReq::setSubjectId);
        Optional.ofNullable(updateTeacherReqDto.getTeacherId()).ifPresent(existingTeacherReq::setTeacherId);

        TeacherReq updatedSubjectReq = teacherReqService.update(existingTeacherReq);
        return ResponseEntity.ok(updatedSubjectReq);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        logService.logRequestAndUser("/teacher-req/{id}", "DELETE");

        teacherReqService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
