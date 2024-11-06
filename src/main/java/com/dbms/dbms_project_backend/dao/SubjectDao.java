package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Subject;
import com.dbms.dbms_project_backend.repository.SubjectRepository;

@Repository
public class SubjectDao implements SubjectRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Subject> rowMapper = (ResultSet rs, int rowNum) -> {
        Subject subject = new Subject();
        subject.setId(rs.getLong("id"));
        subject.setCode(rs.getString("Code"));
        subject.setName(rs.getString("Name"));

        return subject;
    };

    @Override
    public List<Subject> findAll() {
        String sql = "SELECT * FROM subject";
        List<Subject> subjects = jdbcTemplate.query(sql, rowMapper);
        return subjects;
    }

    @Override
    public Optional<Subject> findById(Long id) {
        String sql = "SELECT * FROM subject WHERE id = ?";
        List<Subject> subjects = jdbcTemplate.query(sql, rowMapper, id);

        if (subjects.isEmpty()) {
            return Optional.empty();
        }

        Subject subject = subjects.get(0);
        return Optional.of(subject);
    }

    @SuppressWarnings("null")
    @Override
    public Subject save(Subject subject) {
        String sql = "INSERT INTO subject (Name,Code) VALUES (?, ?)";
        jdbcTemplate.update(sql, subject.getName(), subject.getCode());
        Long subjectId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        subject.setId(subjectId);
        return subject;
    }

    @Override
    public Subject update(Subject subject) {
        String sql = "UPDATE subject SET Name = ?, Code = ? WHERE id = ?";
        jdbcTemplate.update(sql, subject.getName(), subject.getCode(), subject.getId());

        return findById(subject.getId()).get();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM subject WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM subject WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }

    @Override
    public boolean existsBycode(String code) {
        String sql = "SELECT COUNT(*) FROM subject WHERE code = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, code);
        return count > 0;
    }

}
