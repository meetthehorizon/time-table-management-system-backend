package com.dbms.dbms_project_backend.repository;

import com.dbms.dbms_project_backend.model.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectRepository {

    public Subject save(Subject subject);

    public List<Subject> findAll();

    public Optional<Subject> findbySubCode(String subCode);
}
