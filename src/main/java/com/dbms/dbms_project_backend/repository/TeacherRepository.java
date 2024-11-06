package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.Teacher;

public interface TeacherRepository {
    public List<Teacher> findAll();

    public Optional<Teacher> findById(Long id);

    public Teacher update(Teacher existingTeacher);

    public boolean existsById(Long id);
}
