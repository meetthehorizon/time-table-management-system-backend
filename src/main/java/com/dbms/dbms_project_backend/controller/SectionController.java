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

import com.dbms.dbms_project_backend.dto.AddSectionDto;
import com.dbms.dbms_project_backend.dto.UpdateSectionDto;
import com.dbms.dbms_project_backend.model.Section;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.SectionService;

import jakarta.validation.Valid;

@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SCHOOL_INCHARGE')")
@RestController
@RequestMapping("/section")
public class SectionController {
    @Autowired
    private LogService logService;

    @Autowired
    private SectionService sectionService;

    @GetMapping
    public ResponseEntity<List<Section>> findAll() {
        logService.logRequestAndUser("/section", "GET");

        List<Section> sections = sectionService.findAll();
        return ResponseEntity.ok(sections);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Section> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/section/{id}", "GET");

        Section section = sectionService.findById(id);
        return ResponseEntity.ok(section);
    }

    @PostMapping
    public ResponseEntity<Section> save(@Valid @RequestBody AddSectionDto addSectionDto) {
        logService.logRequestAndUser("/section", "POST");

        Section section = new Section().setSchoolId(addSectionDto.getSchoolId())
                .setClassLevel(addSectionDto.getClassLevel()).setRunningYear(addSectionDto.getRunningYear())
                .setSection(addSectionDto.getSection());

        Section savedSection = sectionService.save(section);
        return ResponseEntity.ok(savedSection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Section> delete(@PathVariable Long id) {
        logService.logRequestAndUser("/section/{id}", "DELETE");
        sectionService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/{id}")
    public ResponseEntity<Section> update(@PathVariable Long id,
            @Valid @RequestBody UpdateSectionDto updateSectionDto) {
        logService.logRequestAndUser("/section/{id}", "PUT");

        Section existingSection = sectionService.findById(id);
        Optional.ofNullable(updateSectionDto.getSection()).ifPresent(existingSection::setSection);
        Optional.ofNullable(updateSectionDto.getRunningYear()).ifPresent(existingSection::setRunningYear);
        Optional.ofNullable(updateSectionDto.getClassLevel()).ifPresent(existingSection::setClassLevel);
        Optional.ofNullable(updateSectionDto.getSchoolId()).ifPresent(existingSection::setSchoolId);

        Section updatedSection = sectionService.update(existingSection);
        return ResponseEntity.ok(updatedSection);
    }

    @GetMapping("/school/{id}")
    public ResponseEntity<List<Section>> findBySchoolId(@PathVariable Long id) {
        logService.logRequestAndUser("/section/school/{id}", "GET");

        List<Section> sections = sectionService.findBySchoolId(id);
        return ResponseEntity.ok(sections);
    }

}
