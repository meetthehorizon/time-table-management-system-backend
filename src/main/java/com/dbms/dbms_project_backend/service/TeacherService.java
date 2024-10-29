package com.dbms.dbms_project_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.repository.TeacherRepository;

import java.util.Optional;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    public Optional<Teacher> findById(Long id) {
        return teacherRepository.findById(id);
    }

    public Teacher updateTeacher(Teacher teacher) {
        return teacherRepository.update(teacher);
    }
}
