package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Subject;
import com.dbms.dbms_project_backend.repository.SubjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository subjectRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    public List<Subject> findAll() {
        logger.info("[INFO] Fetching all Subjects");
        List<Subject> subjects = subjectRepository.findAll();

        logger.debug("[DEBUG] Fetched all Subjects");
        return subjects;
    }

    public Subject findById(Long id) {
        logger.info("[INFO] Fetching subject with id: {}", id);
        Optional<Subject> subject = subjectRepository.findById(id);

        if (subject.isPresent()) {
            logger.debug("[DEBUG] Subject found with subCode: {}", id);
            return subject.get();
        } else {
            throw new NotFoundException("Subject", "ID", id);
        }
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting subject with id: {}", id);

        subjectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Subject", "ID", id));
        subjectRepository.deleteById(id);

        logger.debug("[DEBUG] Subject deleted with id: {}", id);

    }

    public Subject update(Subject subject) {
        logger.info("[INFO] Updating subject with subCode: {}", subject.getCode());

        Subject existingSubject = subjectRepository.findById(subject.getId())
                .orElseThrow(() -> new NotFoundException("Subject", "ID", subject.getId()));

        if (!existingSubject.getCode().equals(subject.getCode()) && subjectRepository.existsBycode(subject.getCode())) {
            logger.debug("[DEBUG] Subject with code {} already exists", subject.getCode());
            throw new FieldValueAlreadyExistsException("Subject", "Code", subject.getCode());
        }

        Subject updatedSubject = subjectRepository.update(subject);

        logger.debug("[DEBUG] Subject updated with ID: {}", updatedSubject.getId());
        return updatedSubject;
    }

    public Subject createSubject(Subject subject) {
        logger.info("[INFO] Creating Subject with subCode: {}", subject.getCode());

        if (subjectRepository.existsById(subject.getId())) {
            logger.debug("[DEBUG] Subject Already Exists with ID: {}", subject.getId());
            throw new FieldValueAlreadyExistsException("Subject", "ID", subject.getId());
        }

        subject = subjectRepository.save(subject);
        return subject;
    }
}
