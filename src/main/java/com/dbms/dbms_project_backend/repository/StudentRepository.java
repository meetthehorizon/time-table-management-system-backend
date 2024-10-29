package com.dbms.dbms_project_backend.repository;

import java.util.Optional;

import com.dbms.dbms_project_backend.model.Student;

public interface StudentRepository {
    Optional<Student> findById(Long id);
    Student update(Student student);
}
