package com.dbms.dbms_project_backend.repository;

import java.util.Optional;
import com.dbms.dbms_project_backend.model.Teacher;

public interface TeacherRepository {
    Optional<Teacher> findById(Long id);
    Teacher update(Teacher teacher);
}
