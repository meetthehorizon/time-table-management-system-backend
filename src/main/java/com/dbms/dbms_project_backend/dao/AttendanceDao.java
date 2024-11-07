package com.dbms.dbms_project_backend.dao;

import com.dbms.dbms_project_backend.model.Attendance;
import com.dbms.dbms_project_backend.repository.AttendanceRepository;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository

public class AttendanceDao implements AttendanceRepository {

  @Autowired private JdbcTemplate jdbcTemplate;

  private static RowMapper<Attendance> rowMapper =
      (ResultSet rs, int rowNum) -> {
    Attendance attendance = new Attendance();
    attendance.setId(rs.getLong("id"));
    attendance.setSlotId(rs.getLong("slot_id"));
    attendance.setStudentId(rs.getLong("student_id"));
    attendance.setIsPresent(rs.getBoolean("is_present"));
    attendance.setCurrDate(rs.getDate("curr_date"));
    attendance.setRemark(rs.getString("remark"));
    return attendance;
  };

  @Override
  public List<Attendance> findByStudentId(Long studentId) {
    String sql = "SELECT * FROM attendance WHERE student_id = ?";
    return jdbcTemplate.query(sql, rowMapper, studentId);
  }

  @Override
  public List<Attendance> findBySlotId(Long slotId) {
    String sql = "SELECT * FROM attendance WHERE slot_id = ?";
    return jdbcTemplate.query(sql, rowMapper, slotId);
  }

  @Override
  public List<Attendance> findAll() {
    String sql = "SELECT * FROM attendance";
    return jdbcTemplate.query(sql, rowMapper);
  }

  @Override
  public Attendance save(Attendance attendance) {
    String sql = "INSERT INTO attendance (slot_id, student_id, is_present, " +
                 "curr_date, remark) VALUES (?, ?, ?, ?, ?)";
    jdbcTemplate.update(sql, attendance.getSlotId(), attendance.getStudentId(),
                        attendance.getIsPresent(), attendance.getCurrDate(),
                        attendance.getRemark());

   
    String idSql = "SELECT LAST_INSERT_ID()";
    Long newId = jdbcTemplate.queryForObject(idSql, Long.class);
    attendance.setId(newId);
    return attendance;
  }

  @Override
  public Attendance update(Attendance attendance) {
    String sql = "UPDATE attendance SET slot_id = ?, student_id = ?, " +
                 "is_present = ?, curr_date = ?, remark = ? WHERE id = ?";
    jdbcTemplate.update(sql, attendance.getSlotId(), attendance.getStudentId(),
                        attendance.getIsPresent(), attendance.getCurrDate(),
                        attendance.getRemark(), attendance.getId());
    return attendance;
  }

  @Override
  public Optional<Attendance> findById(Long id) {
    String sql = "SELECT * FROM attendance WHERE id = ?";
    List<Attendance> attendanceList = jdbcTemplate.query(sql, rowMapper, id);
    return attendanceList.isEmpty() ? Optional.empty()
                                    : Optional.of(attendanceList.get(0));
  }
}
