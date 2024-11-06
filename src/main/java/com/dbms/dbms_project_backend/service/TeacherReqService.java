package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.FieldValueAlreadyExistsException;
import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.TeacherReq;
import com.dbms.dbms_project_backend.repository.TeacherReqRepository;
import com.dbms.dbms_project_backend.repository.SchoolRepository;
import com.dbms.dbms_project_backend.repository.SubjectRepository;
import com.dbms.dbms_project_backend.repository.TeacherRepository;

@Service

public class TeacherReqService {
    @Autowired
    private TeacherReqRepository teacherReqRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    private static final Logger logger = LoggerFactory.getLogger(TeacherReqService.class);

    public List<TeacherReq> findBySchoolId(Long schoolId) {
        logger.info("[INFO] Fetching TeacherReq by School ID: " + schoolId);

        List<TeacherReq> teacherReqs = teacherReqRepository.findBySchoolId(schoolId);
        logger.debug("[DEBUG] Fetched TeacherReq by School ID: " + teacherReqs);
        return teacherReqs;
    }

    public List<TeacherReq> findAll() {
        logger.info("[INFO] Fetching All TeacherReq");

        List<TeacherReq> teacherReqs = teacherReqRepository.findAll();
        logger.debug("[DEBUG] Fetched All TeacherReq: " + teacherReqs);
        return teacherReqs;
    }

    public TeacherReq findById(Long id) {
        logger.info("[INFO] Fetching TeacherReq by ID: " + id);

        TeacherReq teacherReq = teacherReqRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("TeacherReq", "id", id));
        logger.debug("[DEBUG] Fetched TeacherReq by ID: " + teacherReq);
        return teacherReq;
    }

    public TeacherReq save(TeacherReq teacherReq) {
        logger.info("[INFO] Saving teacher requirement with id {}", teacherReq.getId());

        if (teacherReq.getSchoolId() != null && !schoolRepository.existsById(teacherReq.getSchoolId())) {
            logger.warn("[WARN] School with ID {} does not exist", teacherReq.getSchoolId());
            throw new NotFoundException("School", "id", teacherReq.getSchoolId());
        }
        if (teacherReq.getSubjectId() != null && !subjectRepository.existsById(teacherReq.getSubjectId())) {
            logger.warn("[WARN] Subject with ID {} does not exist", teacherReq.getSubjectId());
            throw new NotFoundException("Subject", "id", teacherReq.getSubjectId());
        }
        if (teacherReq.getTeacherId() != null && !teacherRepository.existsById(teacherReq.getTeacherId())) {
            logger.warn("[WARN] Teacher with ID {} does not exist", teacherReq.getTeacherId());
            throw new NotFoundException("Teacher", "id", teacherReq.getTeacherId());
        }

        if (teacherReqRepository.existsByUniqueFields(teacherReq)) {
            logger.warn("[WARN] Teacher requirement with id {} already exists", teacherReq.getId());

            teacherReq = teacherReqRepository.findByUniqueFields(teacherReq);
            return teacherReq;
        }

        TeacherReq newTeacherReq = teacherReqRepository.save(teacherReq);
        logger.debug("[DEBUG] Teacher requirement with id {} saved successfully", newTeacherReq.getId());
        return newTeacherReq;
    }

    public void deleteById(Long id) {
        logger.info("[INFO] Deleting teacher requirement with id {}", id);
        if (!teacherReqRepository.existsById(id)) {
            logger.debug("[DEBUG] teacher requirement does not Exists with ID: {}", id);
            throw new NotFoundException("Teacher_Req", "ID", id);
        }
        teacherReqRepository.deleteById(id);
        logger.debug("[DEBUG] Teacher requirement with id {} deleted successfully", id);
    }

    public TeacherReq update(TeacherReq teacherReq) {
        logger.info("[INFO] Updating teacher requirement with id {}", teacherReq.getId());

        if (teacherReqRepository.existsByUniqueFields(teacherReq)) {
            TeacherReq existingTeacherReq = teacherReqRepository.findByUniqueFields(teacherReq);

            if (!existingTeacherReq.getId().equals(teacherReq.getId())) {
                logger.warn("[WARN] teacher requirement with id {} already exists", teacherReq.getId());
                throw new FieldValueAlreadyExistsException("TeacherReq", "all", teacherReq.toString());
            }
        }

        TeacherReq updatedTeacherReq = teacherReqRepository.update(teacherReq);
        logger.debug("[DEBUG] teacher requirement with id {} updated successfully", updatedTeacherReq.getId());
        return updatedTeacherReq;
    }
}
