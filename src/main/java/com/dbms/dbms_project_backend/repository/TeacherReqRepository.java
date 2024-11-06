package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.TeacherReq;

public interface TeacherReqRepository {

    List<TeacherReq> findBySchoolId(Long schoolId);

    List<TeacherReq> findAll();

    Optional<TeacherReq> findById(Long id);

    TeacherReq save(TeacherReq teacherReq);

}
