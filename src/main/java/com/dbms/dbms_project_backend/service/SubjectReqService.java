package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.repository.SubjectReqRepository;

@Service
public class SubjectReqService {
    @Autowired
    private SubjectReqRepository subjectReqRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubjectReqService.class);

    public List<SubjectReq> findAll() {
        logger.info("[INFO] Fetching all subject requirements");

        List<SubjectReq> subjectReqs = subjectReqRepository.findAll();

        logger.debug("[DEBUG] All subject requirements fetched successfully");
        return subjectReqs;
    }

    public SubjectReq findById(Long id) {
        logger.info("[INFO] Fetching subject requirement with id {}", id);

        SubjectReq subjectReq = subjectReqRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubjectReq", "id", id));

        logger.debug("[DEBUG] Subject requirement with id {} fetched successfully", id);
        return subjectReq;
    }

    public SubjectReq save(SubjectReq subjectReq) {
        logger.info("[INFO] Saving subject requirement with id {}", subjectReq.getId());

        if (subjectReqRepository.existsByAllFields(subjectReq)) {
            logger.warn("[WARN] Subject requirement with id {} already exists", subjectReq.getId());
            throw new FieldValueAlreadyExistsException("SubjectReq", "all fields:", subjectReq.toString());
        }

        SubjectReq newSubjectReq = subjectReqRepository.save(subjectReq);

        logger.debug("[DEBUG] Subject requirement with id {} saved successfully", newSubjectReq.getId());
        return newSubjectReq;
    }

    public SubjectReq update(SubjectReq subjectReq) {
        logger.info("[INFO] Updating subject requirement with id {}", subjectReq.getId());

        if (subjectReqRepository.existsByAllFields(subjectReq)) {
            logger.warn("[WARN] Subject requirement with id {} already exists", subjectReq.getId());
            throw new FieldValueAlreadyExistsException("SubjectReq", "all fields:", subjectReq.toString());
        }

        SubjectReq updatedSubjectReq = subjectReqRepository.update(subjectReq);
        logger.debug("[DEBUG] Subject requirement with id {} updated successfully", updatedSubjectReq.getId());
        return updatedSubjectReq;
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting subject requirement with id {}", id);
        if (!subjectReqRepository.existsById(id)) {
            logger.debug("[DEBUG] Subject requirement does not Exists with ID: {}", id);
            throw new NotFoundException("Subject", "ID", id);
        }
        subjectReqRepository.deleteById(id);
        logger.debug("[DEBUG] Subject requirement with id {} deleted successfully", id);
    }
}
