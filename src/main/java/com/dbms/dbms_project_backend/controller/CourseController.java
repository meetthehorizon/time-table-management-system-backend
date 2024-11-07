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

import com.dbms.dbms_project_backend.dto.AddCourseDto;
import com.dbms.dbms_project_backend.dto.UpdateCourseDto;
import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.service.CourseService;
import com.dbms.dbms_project_backend.service.LogService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/course")
@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_SCHOOL_INCHARGE')")
public class CourseController {
    @Autowired
    private LogService logService;

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> findAll() {
        logService.logRequestAndUser("/course", "GET");

        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/school/{id}")
    public ResponseEntity<List<Course>> findBySchoolId(@PathVariable Long id) {
        logService.logRequestAndUser("/course/school/" + id, "GET");

        List<Course> courses = courseService.findBySchoolId(id);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/course/" + id, "GET");

        Course course = courseService.findById(id);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        logService.logRequestAndUser("/course", "DELETE");

        courseService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> update(@PathVariable Long id, @Valid @RequestBody UpdateCourseDto updateCourseDto) {
        logService.logRequestAndUser("/course/" + id, "PUT");

        Course existingCourse = courseService.findById(id).setSectionId(updateCourseDto.getSectionId())
                .setSubjectReqId(updateCourseDto.getSubjectReqId()).setTeacherReqId(updateCourseDto.getTeacherReqId());

        return ResponseEntity.ok(courseService.update(existingCourse));
    }

    @PostMapping
    public ResponseEntity<Course> save(@Valid @RequestBody AddCourseDto addCourseDto) {
        logService.logRequestAndUser("/course", "POST");

        Course course = new Course().setSectionId(addCourseDto.getSectionId())
                .setSubjectReqId(addCourseDto.getSubjectReqId()).setTeacherReqId(addCourseDto.getTeacherReqId());

        Course savedCourse = courseService.save(course);
        return ResponseEntity.ok(savedCourse);
    }
}
