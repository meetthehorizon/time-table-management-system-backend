package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;
import com.dbms.dbms_project_backend.model.Attendance;

public interface AttendanceRepository {

    List<Attendance> findByStudentId(Long id);

    List<Attendance> findBySlotId(Long id);

    List<Attendance> findAll();

    public Attendance save(Attendance attendance);

    public Attendance update(Attendance attendance);

    public Optional<Attendance> findById(Long id);
}
