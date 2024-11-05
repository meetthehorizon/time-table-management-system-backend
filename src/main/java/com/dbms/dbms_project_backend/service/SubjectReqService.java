package com.dbms.dbms_project_backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.repository.SubjectReqRepository;

@Service
public class SubjectReqService {
    @Autowired
    private SubjectReqRepository subjectReqRepository;

    private static final Logger logger = LoggerFactory.getLogger(SubjectReqService.class);

    public SubjectReq findById(Long id) {
        logger.info("[INFO] Fetching subject requirement with id {}", id);

        SubjectReq subjectReq = subjectReqRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SubjectReq", "id", id));

        logger.debug("[DEBUG] Subject requirement with id {} fetched successfully", id);
        return subjectReq;
    }

    public SubjectReq save(SubjectReq subjectReq) {
        logger.info("[INFO] Saving subject requirement with id {}", subjectReq.getId());
        SubjectReq newSubjectReq = subjectReqRepository.save(subjectReq);

        logger.debug("[DEBUG] Subject requirement with id {} saved successfully", newSubjectReq.getId());
        return newSubjectReq;
    }

    public SubjectReq update(SubjectReq subjectReq) {
        logger.info("[INFO] Updating subject requirement with id {}", subjectReq.getId());
        SubjectReq updatedSubjectReq = subjectReqRepository.update(subjectReq);

        logger.debug("[DEBUG] Subject requirement with id {} updated successfully", updatedSubjectReq.getId());
        return updatedSubjectReq;
    }

    public void delete(SubjectReq subjectReq) {
        logger.info("[INFO] Deleting subject requirement with id {}", subjectReq.getId());
        subjectReqRepository.delete(subjectReq);

        logger.debug("[DEBUG] Subject requirement with id {} deleted successfully", subjectReq.getId());
    }
}
