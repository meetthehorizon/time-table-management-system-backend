package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.repository.SubjectRepository;
import com.dbms.dbms_project_backend.repository.TeacherRepository;

@Service
public class TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    public List<Teacher> findAll() {
        logger.info("[INFO] Fetching all Teachers");
        List<Teacher> teachers = teacherRepository.findAll();

        logger.debug("[DEBUG] Fetched all Teachers");
        return teachers;
    }

    public Teacher findById(Long id) {
        logger.info("[INFO] Fetching Teacher with id: {}", id);
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new NotFoundException("Teacher", "id", id));

        logger.debug("[DEBUG] Fetched Teacher with id: {}", id);
        return teacher;
    }

    public Teacher update(Teacher existingTeacher) {
        logger.info("[INFO] Updating Teacher with id: {}", existingTeacher.getId());

        if (existingTeacher.getSubjectId() != null) {
            subjectRepository.findById(existingTeacher.getSubjectId())
                    .orElseThrow(() -> new NotFoundException("Subject", "id", existingTeacher.getSubjectId()));
        }

        Teacher updatedTeacher = teacherRepository.update(existingTeacher);

        logger.debug("[DEBUG] Updated Teacher with id: {}", existingTeacher.getId());
        return updatedTeacher;
    }
}
