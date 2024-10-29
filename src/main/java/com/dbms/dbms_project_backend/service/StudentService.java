package com.dbms.dbms_project_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.model.Student;
import com.dbms.dbms_project_backend.repository.StudentRepository;

import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Optional<Student> findById(Long id) {
        return studentRepository.findById(id);
    }

    public Student updateStudent(Student student) {
        return studentRepository.update(student);
    }
}
