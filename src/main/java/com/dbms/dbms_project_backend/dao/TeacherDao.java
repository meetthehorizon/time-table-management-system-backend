package com.dbms.dbms_project_backend.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.repository.TeacherRepository;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

@Repository
public class TeacherDao implements TeacherRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(TeacherDao.class);

    private RowMapper<Teacher> rowMapper = (ResultSet rs, int rowNum) -> {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("id"));
        teacher.setSubjectId(rs.getLong("subject_id"));

        String positionStr = rs.getString("position");
        if (positionStr != null) {
            Position position = Position.valueOf(positionStr); 
            teacher.setPositions(new HashSet<>(Collections.singleton(position))); 
        } else {
            teacher.setPositions(new HashSet<>()); 
        }

        return teacher;
    };

    @Override
    public Optional<Teacher> findById(Long id) {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        try {
            logger.debug("Executing query to find teacher with id: {}", id);
            Optional<Teacher> teacher = Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper, id));
            logger.info("Teacher found with id: {}", id);
            return teacher;
        } catch (EmptyResultDataAccessException e) {
            logger.warn("No teacher found with id: {}", id);
            return Optional.empty();
        }
    }

    @Override
    public Teacher update(Teacher teacher) {
        String sql = "UPDATE teacher SET subject_id = ?, position = ? WHERE id = ?";
        logger.debug("Executing update for teacher with id: {}", teacher.getId());
        jdbcTemplate.update(sql, teacher.getSubjectId(), teacher.getPositions(), teacher.getId());
        logger.info("Teacher updated with id: {}", teacher.getId());
        return teacher;
    }
}
