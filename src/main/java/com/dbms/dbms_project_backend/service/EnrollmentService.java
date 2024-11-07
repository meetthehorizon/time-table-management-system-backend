package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Enrollment;
import com.dbms.dbms_project_backend.repository.EnrollmentRepository;
import com.dbms.dbms_project_backend.repository.SectionRepository;
import com.dbms.dbms_project_backend.repository.UserRepository;

@Service
public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentService.class);

    public List<Enrollment> findAll() {
        logger.info("[INFO] Fetching all enrollments");

        List<Enrollment> enrollments = enrollmentRepository.findAll();
        logger.debug("[DEBUG] Fetched all enrollments");

        return enrollments;
    }

    public Enrollment findById(Long id) {
        logger.info("[INFO] Fetching enrollment with id: {}", id);

        Enrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment", "id", id));
        logger.debug("[DEBUG] Enrollment found with id: {}", id);

        return enrollment;
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting enrollment with id: {}", id);

        enrollmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Enrollment", "id", id));

        enrollmentRepository.deleteById(id);
        logger.debug("[DEBUG] Enrollment deleted with id: {}", id);
    }

    public Enrollment update(Enrollment enrollment) {
        logger.info("[INFO] Updating enrollment with id: {}", enrollment.getId());

        Optional.ofNullable(enrollment.getSectionId()).ifPresent(sectionId -> {
            sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new NotFoundException("Section", "id", sectionId));
        });

        Optional.ofNullable(enrollment.getStudentId()).ifPresent(studentId -> {
            userRepository.findById(studentId)
                    .orElseThrow(() -> new NotFoundException("User", "id", studentId));
        });

        enrollmentRepository.findById(enrollment.getId())
                .orElseThrow(() -> new NotFoundException("Enrollment", "id", enrollment.getId()));

        if (enrollmentRepository.existsByUniqueFields(enrollment)) {
            Enrollment existingEnrollment = enrollmentRepository.findByUniqueFields(enrollment);

            if (!existingEnrollment.getId().equals(enrollment.getId())) {
                logger.warn("[WARN] Enrollment with id {} already exists", enrollment.getId());
                throw new FieldValueAlreadyExistsException("Enrollment", "id", enrollment.getId());
            }
        }

        Enrollment updatedEnrollment = enrollmentRepository.save(enrollment);
        logger.debug("[DEBUG] Enrollment updated with id: {}", updatedEnrollment.getId());

        return updatedEnrollment;
    }

    public Enrollment save(Enrollment enrollment) {
        logger.info("[INFO] Saving enrollment with id: {}", enrollment.getId());

        Optional.ofNullable(enrollment.getSectionId()).ifPresent(sectionId -> {
            sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new NotFoundException("Section", "id", sectionId));
        });

        Optional.ofNullable(enrollment.getStudentId()).ifPresent(studentId -> {
            userRepository.findById(studentId)
                    .orElseThrow(() -> new NotFoundException("User", "id", studentId));
        });

        if (enrollmentRepository.existsByUniqueFields(enrollment)) {
            logger.warn("[WARN] Enrollment with id {} already exists", enrollment.getId());
            throw new FieldValueAlreadyExistsException("Enrollment", "all", enrollment.toString());
        }

        Enrollment newEnrollment = enrollmentRepository.save(enrollment);
        logger.debug("[DEBUG] Enrollment saved with id: {}", newEnrollment.getId());

        return newEnrollment;
    }
}
