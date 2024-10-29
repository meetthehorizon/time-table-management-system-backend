package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Student;
import com.dbms.dbms_project_backend.repository.StudentRepository;

@Repository
public class StudentDao implements StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(StudentDao.class);

    private RowMapper<Student> rowMapper = (ResultSet rs, int rowNum) -> {
        Student student = new Student();
        student.setUserId(rs.getLong("user_id"));
        student.setParentId(rs.getLong("parent_id"));
        return student;
    };

    @Override
    public Optional<Student> findById(Long id) {
        String sql = "SELECT * FROM students WHERE user_id = ?";
        try {
            logger.debug("Executing query to find student with id: {}", id);
            Optional<Student> student = Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
            logger.info("Student found with id: {}", id);
            return student;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No student found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Student update(Student student) {
        String sql = "UPDATE students SET parent_id = ? WHERE user_id = ?";
        logger.debug("Executing update for student with id: {}", student.getUserId());
        jdbcTemplate.update(sql, student.getParentId(), student.getUserId());
        logger.info("Student updated with id: {}", student.getUserId());
        return student;
    }
}
