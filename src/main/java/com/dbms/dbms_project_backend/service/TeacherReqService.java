package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.TeacherReq;
import com.dbms.dbms_project_backend.repository.TeacherReqRepository;

@Service
public class TeacherReqService {
    @Autowired
    private TeacherReqRepository teacherReqRepository;

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

        TeacherReq teacherReq = teacherReqRepository.findById(id).orElseThrow(() -> new NotFoundException("TeacherReq", "id", id));
        logger.debug("[DEBUG] Fetched TeacherReq by ID: " + teacherReq);
        return teacherReq;
    }

    public TeacherReq save(TeacherReq teacherReq) {
        logger.info("[INFO] Saving TeacherReq: " + teacherReq);

        TeacherReq savedTeacherReq = teacherReqRepository.save(teacherReq);
        logger.debug("[DEBUG] Saved TeacherReq: " + savedTeacherReq);
        return savedTeacherReq;
    }
}
