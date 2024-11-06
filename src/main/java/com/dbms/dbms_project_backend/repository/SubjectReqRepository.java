package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.SubjectReq;

public interface SubjectReqRepository {
    public Optional<SubjectReq> findById(Long id);

    public SubjectReq save(SubjectReq subjectReq);

    public SubjectReq update(SubjectReq subjectReq);

    public List<SubjectReq> findAll();

    public boolean existsByAllFields(SubjectReq subjectReq);

    public void deleteById(Long id);

    public boolean existsById(Long id);
}
