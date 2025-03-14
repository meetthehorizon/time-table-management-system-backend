package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.TeacherReq;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.repository.TeacherReqRepository;

@Repository
public class TeacherReqDao implements TeacherReqRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static RowMapper<TeacherReq> rowMapper = (rs, rowNum) -> {
        TeacherReq teacherReq = new TeacherReq();
        teacherReq.setId(rs.getLong("id"));
        teacherReq.setSchoolId(rs.getLong("school_id") == 0 ? null : rs.getLong("school_id"));
        teacherReq.setTeacherId(rs.getLong("teacher_id") == 0 ? null : rs.getLong("teacher_id"));
        teacherReq.setSubjectId(rs.getLong("subject_id") == 0 ? null : rs.getLong("subject_id"));
        teacherReq.setPosition(rs.getString("position") == null ? null : Position.fromString(rs.getString("position")));
        return teacherReq;
    };

    @Override
    public List<TeacherReq> findBySchoolId(Long schoolId) {
        String sql = "SELECT * FROM teacher_req WHERE school_id = ?";

        List<TeacherReq> teacherReqs = jdbcTemplate.query(sql, rowMapper, schoolId);
        return teacherReqs;
    }

    @Override
    public List<TeacherReq> findAll() {
        String sql = "SELECT * FROM teacher_req";

        List<TeacherReq> teacherReqs = jdbcTemplate.query(sql, rowMapper);
        return teacherReqs;
    }

    @Override
    public Optional<TeacherReq> findById(Long id) {
        String sql = "SELECT * FROM teacher_req WHERE id = ?";
        List<TeacherReq> teacherReqs = jdbcTemplate.query(sql, rowMapper, id);

        if (teacherReqs.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(teacherReqs.get(0));
        }
    }

    @Override
    public TeacherReq save(TeacherReq teacherReq) {
        String sql = "INSERT INTO teacher_req (school_id, position, teacher_id, subject_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, teacherReq.getSchoolId(), teacherReq.getPosition().toString(),
                teacherReq.getTeacherId(),
                teacherReq.getSubjectId());

        Long teacherReqId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        teacherReq.setId(teacherReqId);

        return teacherReq;
    }

    @Override
    public boolean existsByUniqueFields(TeacherReq teacherReq) {
        String sql = "SELECT * FROM teacher_req WHERE school_id = ? AND subject_id = ? AND position = ?";
        List<TeacherReq> teacherReqs = jdbcTemplate.query(sql, rowMapper, teacherReq.getSchoolId(),
                teacherReq.getSubjectId(), teacherReq.getPosition().toString());
        return !teacherReqs.isEmpty();
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM teacher_req WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, id) > 0;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM teacher_req WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public TeacherReq update(TeacherReq teacherReq) {
        String sql = "UPDATE teacher_req SET school_id = ?, position = ?, teacher_id = ?, subject_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, teacherReq.getSchoolId(), teacherReq.getPosition().toString(),
                teacherReq.getTeacherId(), teacherReq.getSubjectId(), teacherReq.getId());
        return teacherReq;
    }

    @Override
    public TeacherReq findByUniqueFields(TeacherReq teacherReq) {
        String sql = "SELECT * FROM teacher_req WHERE school_id = ? AND subject_id = ? AND position = ?";
        TeacherReq existingTeachReq = jdbcTemplate.queryForObject(sql, rowMapper, teacherReq.getSchoolId(),
                teacherReq.getSubjectId(), teacherReq.getPosition().toString());

        return existingTeachReq;
    }
}
