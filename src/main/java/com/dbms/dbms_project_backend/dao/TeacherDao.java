package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Teacher;
import com.dbms.dbms_project_backend.model.enumerations.Position;
import com.dbms.dbms_project_backend.repository.TeacherRepository;

@Repository
public class TeacherDao implements TeacherRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    private final RowMapper<Teacher> teacherRowMapper = (rs, rowNum) -> {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getLong("id"));
        teacher.setSubjectId(rs.getLong("subject_id"));
        teacher.setPosition(Position.valueOf(rs.getString("position")));
        return teacher;
    };

    @Override
    public List<Teacher> findAll() {
        String sql = "SELECT * FROM teachers";
        List<Teacher> teachers = jdbcTemplate.query(sql, teacherRowMapper);

        teachers.forEach((teacher) -> {
            teacher.setUser(userDao.findById(teacher.getId()).get());
        });

        return teachers;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        String sql = "SELECT * FROM teachers WHERE id = ?";
        Teacher teacher = jdbcTemplate.queryForObject(sql, teacherRowMapper, id);

        if (teacher != null) {
            teacher.setUser(userDao.findById(teacher.getId()).get());
        }
        return Optional.of(teacher);
    }

    @Override
    public Teacher update(Teacher existingTeacher) {
        String sql = "UPDATE teachers SET subject_id = ?, position = ? WHERE id = ?";
        jdbcTemplate.update(sql, existingTeacher.getSubjectId(), existingTeacher.getPosition().toString(),
                existingTeacher.getId());

        return existingTeacher;
    }

}
