package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.School;
import com.dbms.dbms_project_backend.repository.SchoolRepository;

@Service
public class SchoolService {
    @Autowired
    private SchoolRepository schoolRepository;

    private static final Logger logger = LoggerFactory.getLogger(SchoolService.class);

    public List<School> findAll() {
        logger.info("[INFO] Fetching all Schools");
        return schoolRepository.findAll();
    }

    public School findById(Long id) {
        logger.info("[INFO] Fetching School with id: {}", id);
        Optional<School> schoolOpt = schoolRepository.findById(id);

        if (schoolOpt.isPresent()) {
            logger.debug("[DEBUG] Found School with id: {}", id);
            return schoolOpt.get();
        } else {
            logger.debug("[DEBUG] No School with schoolId: {}", id);
            throw new NotFoundException("School", "id", id);
        }
    }

    public School add(School school) {
        logger.info("[INFO] Adding School: {}", school);

        if (schoolRepository.existsByEmail(school.getEmail())) {
            logger.debug("[DEBUG] School Already Exists with schoolEmail: {}", school.getEmail());
            throw new FieldValueAlreadyExistsException("School", "email", school.getEmail());
        }

        if (schoolRepository.existsByPhone(school.getPhone())) {
            logger.debug("[DEBUG] School Already Exists with schoolPhone: {}", school.getPhone());
            throw new FieldValueAlreadyExistsException("School", "phone", school.getPhone());
        }

        school = schoolRepository.save(school);
        return school;
    }

    public School update(School school) {
        logger.info("[INFO] Updating School with id: {}", school.getId());

        School existingSchool = schoolRepository.findById(school.getId())
                .orElseThrow(() -> new NotFoundException("School", "id", school.getId()));

        if (!existingSchool.getEmail().equals(school.getEmail())) {
            if (schoolRepository.existsByEmail(school.getEmail())) {
                logger.debug("[DEBUG] School Already Exists with schoolEmail: {}", school.getEmail());
                throw new FieldValueAlreadyExistsException("School", "email", school.getEmail());
            }
        }

        if (!existingSchool.getPhone().equals(school.getPhone())) {
            if (schoolRepository.existsByPhone(school.getPhone())) {
                logger.debug("[DEBUG] School Already Exists with schoolPhone: {}", school.getPhone());
                throw new FieldValueAlreadyExistsException("School", "phone", school.getPhone());
            }
        }

        School updatedSchool = schoolRepository.update(school);
        logger.debug("[DEBUG] Updated School with id: {}", school.getId());

        return updatedSchool;
    }
}
