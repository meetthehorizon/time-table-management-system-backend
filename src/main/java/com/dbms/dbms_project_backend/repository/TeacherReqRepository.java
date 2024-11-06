package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.TeacherReq;

public interface TeacherReqRepository {

    List<TeacherReq> findBySchoolId(Long schoolId);

    List<TeacherReq> findAll();

    Optional<TeacherReq> findById(Long id);

    TeacherReq save(TeacherReq teacherReq);

    public boolean existsByAllFields(TeacherReq teacherReq);

    public void deleteById(Long id);

    public boolean existsById(Long id);

    public TeacherReq update(TeacherReq teacherReq);

    void setByAllFields(TeacherReq teacherReq);
}
