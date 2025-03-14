package com.dbms.dbms_project_backend.controller;

import com.dbms.dbms_project_backend.dto.AddSchoolDto;
import com.dbms.dbms_project_backend.dto.UpdateSchoolDto;
import com.dbms.dbms_project_backend.model.School;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SchoolService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
@RequestMapping("/school")
@RestController
public class SchoolController {
  @Autowired
  private SchoolService schoolService;

  @Autowired
  private LogService logService;

  private static final Logger logger = LoggerFactory.getLogger(SchoolController.class);

  @GetMapping()
  public ResponseEntity<List<School>> findAll() {
    logService.logRequestAndUser("/school", "GET");

    List<School> schools = schoolService.findAll();
    return ResponseEntity.ok(schools);
  }

  @GetMapping("/{id}")
  public ResponseEntity<School> findById(@PathVariable Long id) {
    logService.logRequestAndUser("/school/" + id, "GET");

    School school = schoolService.findById(id);
    return ResponseEntity.ok(school);
  }

  @PostMapping()
  public ResponseEntity<School> addSchool(@Valid @RequestBody AddSchoolDto addSchoolDto) {
    logService.logRequestAndUser("/school", "POST");

    School school = new School();
    school.setName(addSchoolDto.getName())
        .setAddress(addSchoolDto.getAddress())
        .setPhone(addSchoolDto.getPhone())
        .setEmail(addSchoolDto.getEmail());

    school = schoolService.add(school);
    return ResponseEntity.ok(school);
  }

  @PutMapping("/{id}")
  public ResponseEntity<School> editSchool(@PathVariable Long id,
      @Valid @RequestBody UpdateSchoolDto updatedSchool) {
    logService.logRequestAndUser("/school" + id, "PUT");

    School existingSchool = schoolService.findById(id);
    logger.info("[INFO] Editing School with schoolId: {}", id);

    Optional.ofNullable(updatedSchool.getName())
        .ifPresent(existingSchool::setName);
    Optional.ofNullable(updatedSchool.getAddress())
        .ifPresent(existingSchool::setAddress);
    Optional.ofNullable(updatedSchool.getPhone())
        .ifPresent(existingSchool::setPhone);
    Optional.ofNullable(updatedSchool.getEmail())
        .ifPresent(existingSchool::setEmail);

    schoolService.update(existingSchool);
    return ResponseEntity.ok(existingSchool);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteSchool(@PathVariable Long id) {
    logService.logRequestAndUser("/school" + id, "DELETE");

    logger.info("[INFO] Deleting School with schoolId: {}", id);
    schoolService.delete(id);
    return ResponseEntity.ok().build();
  }
}
