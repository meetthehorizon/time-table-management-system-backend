package com.dbms.dbms_project_backend.controller;

import com.dbms.dbms_project_backend.dto.AddEnrollmentDto;
import com.dbms.dbms_project_backend.dto.UpdateEnrollmentDto;
import com.dbms.dbms_project_backend.model.Enrollment;
import com.dbms.dbms_project_backend.service.EnrollmentService;
import com.dbms.dbms_project_backend.service.LogService;
import jakarta.validation.Valid;
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

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SCHOOL_INCHARGE')")
@RestController
@RequestMapping("/enrollment")
public class EnrollmentController {
  @Autowired
  private LogService logService;

  @Autowired
  private EnrollmentService enrollmentService;

  @GetMapping
  public ResponseEntity<List<Enrollment>> findAll() {
    logService.logRequestAndUser("/enrollment", "GET");

    List<Enrollment> enrollments = enrollmentService.findAll();
    return ResponseEntity.ok(enrollments);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Enrollment> findById(@PathVariable Long id) {
    logService.logRequestAndUser("/enrollment/" + id, "GET");

    Enrollment enrollment = enrollmentService.findById(id);
    return ResponseEntity.ok(enrollment);
  }

  @PostMapping
  public ResponseEntity<Enrollment> save(@Valid @RequestBody AddEnrollmentDto addEnrollmentDto) {
    logService.logRequestAndUser("/enrollment", "POST");

    Enrollment enrollment = new Enrollment()
        .setStudentId(addEnrollmentDto.getStudentId())
        .setSectionId(addEnrollmentDto.getSectionId());

    Enrollment savedEnrollment = enrollmentService.save(enrollment);
    return ResponseEntity.ok(savedEnrollment);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Enrollment> update(@PathVariable Long id,
      @Valid @RequestBody UpdateEnrollmentDto updateEnrollmentDto) {
    logService.logRequestAndUser("/enrollment", "PUT");

    Enrollment existingEnrollment = enrollmentService.findById(id);

    Optional.ofNullable(updateEnrollmentDto.getStudentId())
        .ifPresent(existingEnrollment::setStudentId);
    Optional.ofNullable(updateEnrollmentDto.getSectionId())
        .ifPresent(existingEnrollment::setSectionId);

    Enrollment updatedEnrollment = enrollmentService.update(existingEnrollment);
    return ResponseEntity.ok(updatedEnrollment);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    logService.logRequestAndUser("/enrollment/" + id, "DELETE");

    enrollmentService.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
