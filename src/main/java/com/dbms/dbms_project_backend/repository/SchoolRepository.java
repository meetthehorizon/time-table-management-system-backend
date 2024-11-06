package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.School;

public interface SchoolRepository {
    public List<School> findAll();

    public Optional<School> findById(Long id);

    public School save(School school);

    public School update(School school);

    public void deleteById(Long id);

    public boolean existsByEmail(String email);

    public boolean existsByPhone(String phone);

    public boolean existsById(Long id);
}
