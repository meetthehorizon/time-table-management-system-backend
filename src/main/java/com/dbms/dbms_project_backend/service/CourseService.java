package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.repository.CourseRepository;
import com.dbms.dbms_project_backend.repository.SectionRepository;
import com.dbms.dbms_project_backend.repository.SubjectReqRepository;
import com.dbms.dbms_project_backend.repository.TeacherReqRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SectionRepository sectionRepository;
    @Autowired
    private SubjectReqRepository subjectReqRepository;

    @Autowired
    private TeacherReqRepository teacherReqRepository;
    private static final Logger logger = LoggerFactory.getLogger(CourseService.class);

    public List<Course> findAll() {
        logger.info("[INFO] Fetching all Courses");
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        logger.info("[INFO] Fetching Courses with id: {}", id);
        Optional<Course> courseOpt = courseRepository.findById(id);

        if (courseOpt.isPresent()) {
            logger.debug("[DEBUG] Found Course with id: {}", id);
            return courseOpt.get();
        } else {
            logger.debug("[DEBUG] No Course with id: {}", id);
            throw new NotFoundException("Course", "id", id);
        }
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting Course with id: {}", id);
        courseRepository.deleteById(id);
        logger.debug("[DEBUG] Deleted Course with id: {}", id);
    }

    public Course update(Course course) {
        logger.info("[INFO] Updating Course with id: {}", course.getId());

        if (!courseRepository.existsById(course.getId())) {
            logger.debug("[DEBUG] No Course with id: {}", course.getId());
            throw new NotFoundException("Course", "id", course.getId());
        }

        if (!sectionRepository.existsById(course.getSectionId())) {
            logger.debug("[DEBUG] No Section with id: {}", course.getSectionId());
            throw new NotFoundException("Section", "id", course.getSectionId());
        }

        if (!subjectReqRepository.existsById(course.getSubjectReqId())) {
            logger.debug("[DEBUG] No Subject Requirement with id: {}", course.getSubjectReqId());
            throw new NotFoundException("Subject Requirement", "id", course.getSubjectReqId());
        }

        if (!teacherReqRepository.existsById(course.getTeacherReqId())) {
            logger.debug("[DEBUG] No Teacher Requirement with id: {}", course.getTeacherReqId());
            throw new NotFoundException("Teacher Requirement", "id", course.getTeacherReqId());
        }

        if (courseRepository.existsByUniqueFields(course)) {
            Course existingCourse = courseRepository.findByUniqueFields(course);

            if (!existingCourse.getId().equals(course.getId())) {
                logger.warn("[WARN] Course already exists: " + course.toString());
                throw new FieldValueAlreadyExistsException("Course", "all", course.toString());
            }
        }

        Course updatedCourse = courseRepository.update(course);
        logger.debug("[DEBUG] Updated Course with id: {}", course.getId());
        return updatedCourse;
    }

    public Course add(Course course) {
        logger.info("[INFO] Adding new Course with id: {}", course.getId());

        Optional.ofNullable(course.getSectionId()).ifPresent(sectionId -> {
            if (!sectionRepository.existsById(sectionId)) {
                logger.debug("[DEBUG] No Section with id: {}", sectionId);
                throw new NotFoundException("Section", "id", sectionId);
            }
        });

        Optional.ofNullable(course.getSubjectReqId()).ifPresent(subjectReqId -> {
            if (!subjectReqRepository.existsById(subjectReqId)) {
                logger.debug("[DEBUG] No Subject Requirement with id: {}", subjectReqId);
                throw new NotFoundException("Subject Requirement", "id", subjectReqId);
            }
        });

        Optional.ofNullable(course.getTeacherReqId()).ifPresent(teacherReqId -> {
            if (!teacherReqRepository.existsById(teacherReqId)) {
                logger.debug("[DEBUG] No Teacher Requirement with id: {}", teacherReqId);
                throw new NotFoundException("Teacher Requirement", "id", teacherReqId);
            }
        });

        if (courseRepository.existsByUniqueFields(course)) {
            logger.warn("[WARN] Course already exists: " + course.toString());
            throw new FieldValueAlreadyExistsException("Course", "all", course.toString());
        }

        Course newCourse = courseRepository.save(course);
        logger.debug("[DEBUG] Added new Course with id: {}", newCourse.getId());
        return newCourse;
    }

    public Course save(Course course) {
        logger.info("[INFO] Saving Course with id: {}", course.getId());

        Optional.ofNullable(course.getSectionId()).ifPresent(sectionId -> {
            if (!sectionRepository.existsById(sectionId)) {
            logger.debug("[DEBUG] No Section with id: {}", sectionId);
            throw new NotFoundException("Section", "id", sectionId);
            }
        });

        Optional.ofNullable(course.getSubjectReqId()).ifPresent(subjectReqId -> {
            if (!subjectReqRepository.existsById(subjectReqId)) {
            logger.debug("[DEBUG] No Subject Requirement with id: {}", subjectReqId);
            throw new NotFoundException("Subject Requirement", "id", subjectReqId);
            }
        });

        Optional.ofNullable(course.getTeacherReqId()).ifPresent(teacherReqId -> {
            if (!teacherReqRepository.existsById(teacherReqId)) {
            logger.debug("[DEBUG] No Teacher Requirement with id: {}", teacherReqId);
            throw new NotFoundException("Teacher Requirement", "id", teacherReqId);
            }
        });

        if (courseRepository.existsByUniqueFields(course)) {
            logger.warn("[WARN] Course already exists: " + course.toString());
            if (!courseRepository.findByUniqueFields(course).getId().equals(course.getId())) {
                logger.warn("[WARN] Course already exists: " + course.toString());
                throw new FieldValueAlreadyExistsException("Course", "all", course.toString());
            }
        }

        Course newCourse = courseRepository.save(course);
        logger.debug("[DEBUG] Saved Course with id: {}", newCourse.getId());
        return newCourse;
    }
}
