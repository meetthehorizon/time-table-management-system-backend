package com.dbms.dbms_project_backend.dao;

import java.sql.ResultSet;
import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Section;
import com.dbms.dbms_project_backend.repository.SectionRepository;

@Repository
public class SectionDao implements SectionRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Section> rowMapper = (ResultSet rs, int rowNum) -> {
        Section section = new Section();
        section.setId(rs.getLong("id"));
        section.setSchoolId(rs.getLong("school_id"));
        section.setClassLevel(rs.getInt("class"));

        int yearValue = rs.getInt("running_year");
        section.setRunningYear(Year.of(yearValue));

        section.setClassTeacherId(rs.getLong("class_teacher_id"));
        section.setSection(rs.getString("section"));
        return section;
    };

    @Override
    public Section save(Section section) {
        String sql = "INSERT INTO sections (school_id, class, running_year, class_teacher_id, section) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                section.getSchoolId(),
                section.getClassLevel(),
                section.getRunningYear().getValue(),
                section.getClassTeacherId(),
                section.getSection());

        Long sectionId = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        section.setId(sectionId);

        return section;
    }

    @Override
    public List<Section> findAll() {

        String sql = "SELECT * FROM sections";
        List<Section> sections = jdbcTemplate.query(sql, rowMapper);
        return sections;
    }

    @Override
    public Optional<Section> findById(Long id) {
        String sql = "SELECT * FROM  sections WHERE id = ?";
        List<Section> sections = jdbcTemplate.query(sql, rowMapper, id);

        if (sections.isEmpty()) {
            return Optional.empty();
        }
        Section section = sections.get(0);
        return Optional.of(section);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM sections WHERE id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public Section update(Section section) {
        String sql = "UPDATE sections SET school_id = ?, class = ?, running_year = ?, class_teacher_id = ?, section = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                section.getSchoolId(),
                section.getClassLevel(),
                section.getRunningYear().getValue(), // Convert Year to int
                section.getClassTeacherId(),
                section.getSection(),
                section.getId());

        return findById(section.getId()).orElse(null);
    }

    @Override
    public List<Section> findBySchoolId(Long id) {
        String sql = "SELECT * FROM sections WHERE school_id = ?";
        List<Section> sections = jdbcTemplate.query(sql, rowMapper, id);

        return sections;
    }

    @Override
    public boolean existsByUniqueFields(Section section) {
        String sql = "SELECT * FROM sections WHERE school_id = ? AND class = ? AND running_year = ? AND section = ?";

        List<Section> sections = jdbcTemplate.query(sql, rowMapper, section.getSchoolId(), section.getClassLevel(),
                section.getRunningYear().getValue(), section.getSection());

        return !sections.isEmpty();
    }

    @Override
    public Section findByUniqueFields(Section section) {
        String sql = "SELECT * FROM sections WHERE school_id = ? AND class = ? AND running_year = ? AND section = ?";

        Section existingSection = jdbcTemplate.queryForObject(sql, rowMapper, section.getSchoolId(),
                section.getClassLevel(),
                section.getRunningYear().getValue(), section.getSection());

        return existingSection;
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "SELECT * FROM sections WHERE id = ?";
        List<Section> sections = jdbcTemplate.query(sql, rowMapper, id);

        return !sections.isEmpty();
    }
}
