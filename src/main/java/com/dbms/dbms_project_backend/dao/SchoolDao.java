package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.School;
import com.dbms.dbms_project_backend.repository.SchoolRepository;

@Repository
public class SchoolDao implements SchoolRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<School> rowMapper = (ResultSet rs, int rowNum) -> {
        School school = new School();
        school.setId(rs.getLong("id"));
        school.setName(rs.getString("name"));
        school.setAddress(rs.getString("address"));
        school.setPhone(rs.getString("phone"));
        school.setEmail(rs.getString("email"));

        return school;
    };

    @Override
    public List<School> findAll() {
        String sql = "SELECT * FROM school";
        List<School> schools = jdbcTemplate.query(sql, rowMapper);
        return schools;
    }

    @Override
    public Optional<School> findById(Long id) {
        String sql = "SELECT * FROM school WHERE id = ?";
        List<School> schools = jdbcTemplate.query(sql, rowMapper, id);
        if (schools.isEmpty()) {
            return Optional.empty();
        }
        School school = schools.get(0);
        return Optional.of(school);
    }

    @Override
    public School save(School school) {
        String sql = "INSERT INTO school (name, address, phone, email) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, school.getName(), school.getAddress(), school.getPhone(), school.getEmail());
        Long schoolId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);

        school.setId(schoolId);
        return school;
    }

    @Override
    public School update(School school) {
        String sql = "UPDATE school SET name = ?, address = ?, phone = ?, email = ? WHERE id = ?";
        jdbcTemplate.update(sql, school.getName(), school.getAddress(), school.getPhone(), school.getEmail(),
                school.getId());
        return findById(school.getId()).get();
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM school WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        String sql = "SELECT COUNT(*) FROM school WHERE email = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count > 0;
    }

    @Override
    public boolean existsByPhone(String phone) {
        String sql = "SELECT COUNT(*) FROM school WHERE phone = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, phone);
        return count > 0;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT COUNT(*) FROM school WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
