package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Enrollment;
import com.dbms.dbms_project_backend.repository.EnrollmentRepository;

@Repository
public class EnrollmentDao implements EnrollmentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Enrollment> rowMapper = (rs, rowNum) -> {
        return new Enrollment()
                .setId(rs.getLong("id"))
                .setStudentId(rs.getLong("student_id"))
                .setSectionId(rs.getLong("section_id"));
    };

    @Override
    public List<Enrollment> findAll() {
        String sql = "SELECT * FROM enrollment";
        List<Enrollment> enrollments = jdbcTemplate.query(sql, rowMapper);

        return enrollments;
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        String sql = "SELECT * FROM enrollment WHERE id = ?";
        List<Enrollment> enrollments = jdbcTemplate.query(sql, rowMapper, id);

        if (enrollments.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(enrollments.get(0));
        }
    }

    @Override
    public Enrollment save(Enrollment enrollment) {
        String sql = "INSERT INTO enrollment (student_id, section_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, enrollment.getStudentId(), enrollment.getSectionId());

        enrollment.setId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
        return enrollment;
    }

    @Override
    public Enrollment update(Enrollment enrollment) {
        String sql = "UPDATE enrollment SET student_id = ?, section_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, enrollment.getStudentId(), enrollment.getSectionId(), enrollment.getId());

        return enrollment;
    }

    @Override
    public boolean existsByUniqueFields(Enrollment enrollment) {
        String sql = "SELECT COUNT(*) FROM enrollment WHERE student_id = ? AND section_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, enrollment.getStudentId(),
                enrollment.getSectionId());

        return count > 0;
    }

    @Override
    public Enrollment findByUniqueFields(Enrollment enrollment) {
        String sql = "SELECT * FROM enrollment WHERE student_id = ? AND section_id = ?";
        Enrollment existingEnrollment = jdbcTemplate.queryForObject(sql, rowMapper, enrollment.getStudentId(),
                enrollment.getSectionId());

        return existingEnrollment;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM enrollment WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
