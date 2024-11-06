package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.SubjectReq;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.repository.SubjectReqRepository;

@Repository
public class SubjectReqDao implements SubjectReqRepository {
    private RowMapper<SubjectReq> subjectReqRowMapper = (rs, rowNum) -> {
        SubjectReq subjectReq = new SubjectReq();

        subjectReq.setId(rs.getLong("id")).setSubjectId(rs.getLong("subject_id")).setClassLevel(rs.getInt("class"))
                .setNumLecture(rs.getInt("num_lectures")).setNumLab(rs.getInt("num_lab")).setTeacherPosition(Position
                        .fromString(rs.getString("position")))
                .setAttendanceCriteria(rs.getInt("attendance_criteria"));

        return subjectReq;
    };

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SubjectReq> findAll() {
        String sql = "SELECT * FROM subject_req";
        List<SubjectReq> subjectReqs = jdbcTemplate.query(sql, subjectReqRowMapper);
        return subjectReqs;
    }

    @Override
    public Optional<SubjectReq> findById(Long id) {
        String sql = "SELECT * FROM subject_req WHERE id = ?";
        List<SubjectReq> subjectReqs = jdbcTemplate.query(sql, subjectReqRowMapper, id);
        if (subjectReqs.isEmpty()) {
            return Optional.empty();
        }

        SubjectReq subjectReq = subjectReqs.get(0);
        return Optional.of(subjectReq);
    }

    @Override
    public SubjectReq save(SubjectReq subjectReq) {
        String sql = "INSERT INTO subject_req (subject_id, class, num_lectures, num_lab, position, attendance_criteria) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, subjectReq.getSubjectId(), subjectReq.getClassLevel(), subjectReq.getNumLecture(),
                subjectReq.getNumLab(), subjectReq.getTeacherPosition().toString(), subjectReq.getAttendanceCriteria());
        Long subjectReqId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        subjectReq.setId(subjectReqId);
        return subjectReq;
    }

    @Override
    public SubjectReq update(SubjectReq subjectReq) {
        String sql = "UPDATE subject_req SET subject_id = ?, class = ?, num_lectures = ?, num_lab = ?, position = ?, attendance_criteria = ? WHERE id = ?";
        jdbcTemplate.update(sql, subjectReq.getSubjectId(), subjectReq.getClassLevel(), subjectReq.getNumLecture(),
                subjectReq.getNumLab(), subjectReq.getTeacherPosition().toString(), subjectReq.getAttendanceCriteria(),
                subjectReq.getId());
        return subjectReq;
    }

    @Override
    public boolean existsByAllFields(SubjectReq subjectReq) {
        String sql = "SELECT * FROM subject_req WHERE subject_id = ? AND class = ? AND num_lectures = ? AND num_lab = ? AND position = ? AND attendance_criteria = ?";
        List<SubjectReq> subjectReqs = jdbcTemplate.query(sql, subjectReqRowMapper, subjectReq.getSubjectId(),
                subjectReq.getClassLevel(), subjectReq.getNumLecture(), subjectReq.getNumLab(),
                subjectReq.getTeacherPosition().toString(), subjectReq.getAttendanceCriteria());
        return !subjectReqs.isEmpty();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM subject_req WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT * FROM subject_req WHERE id = ?";
        List<SubjectReq> subjectReqs = jdbcTemplate.query(sql, subjectReqRowMapper, id);
        return !subjectReqs.isEmpty();
    }

    @Override
    public void setIdByAllFields(SubjectReq subjectReq) {
        String sql = "SELECT id FROM subject_req WHERE subject_id = ? AND class = ? AND num_lectures = ? AND num_lab = ? AND position = ? AND attendance_criteria = ?";
        Long id = jdbcTemplate.queryForObject(sql, Long.class, subjectReq.getSubjectId(), subjectReq.getClassLevel(),
                subjectReq.getNumLecture(), subjectReq.getNumLab(), subjectReq.getTeacherPosition().toString(),
                subjectReq.getAttendanceCriteria());

        subjectReq.setId(id);
    }
}
