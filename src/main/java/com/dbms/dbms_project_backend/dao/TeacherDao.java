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

        if (rs.getString("position") != null) {
            teacher.setPosition(Position.valueOf(rs.getString("position")));
        } else {
            teacher.setPosition(null);
        }
        return teacher;
    };

    @Override
    public List<Teacher> findAll() {
        String sql = "SELECT * FROM teacher";
        List<Teacher> teachers = jdbcTemplate.query(sql, teacherRowMapper);

        teachers.forEach((teacher) -> {
            teacher.setUser(userDao.findById(teacher.getId()).get());
        });

        return teachers;
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        String sql = "SELECT * FROM teacher WHERE id = ?";
        List<Teacher> teachers = jdbcTemplate.query(sql, teacherRowMapper, id);

        if (teachers.isEmpty()) {
            return Optional.empty();
        }

        Teacher teacher = teachers.get(0);
        teacher.setUser(userDao.findById(teacher.getId()).get());

        return Optional.of(teacher);
    }

    @Override
    public Teacher update(Teacher teacher) {
        String sql = "UPDATE teacher SET subject_id = ?, position = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                teacher.getSubjectId() != 0 ? teacher.getSubjectId() : null,
                teacher.getPosition() != null ? teacher.getPosition().toString() : null,
                teacher.getId());

        return teacher;
    }

    @Override
    public boolean existsById(Long id){
        String sql = "SELECT COUNT(*) FROM teacher WHERE id = ?";
        return  jdbcTemplate.queryForObject(sql,Integer.class,id)>0;
    }

}
