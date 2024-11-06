package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Enrollment;

@Repository
public interface EnrollmentRepository {
    public List<Enrollment> findAll();

    public Optional<Enrollment> findById(Long id);

    public Enrollment save(Enrollment enrollment);

    public Enrollment update(Enrollment enrollment);

    public void deleteById(Long id);

    public boolean existsByUniqueFields(Enrollment enrollment);

    public Enrollment findByUniqueFields(Enrollment enrollment);
}
