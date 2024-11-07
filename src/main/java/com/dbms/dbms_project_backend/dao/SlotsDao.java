package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.dbms.dbms_project_backend.model.Slots;
import com.dbms.dbms_project_backend.model.enumerations.Day;
import com.dbms.dbms_project_backend.repository.SlotsRepository;

public class SlotsDao implements SlotsRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Slots> rowMapper = (rs, rowNum) -> {
        return new Slots().setId(rs.getLong("id")).setCourseId(rs.getLong("course_id"))
                .setDay(Day.fromString(rs.getString("day"))).setStartTime(rs.getInt("start_time"))
                .setEndTime(rs.getInt("end_time"));
    };

    @Override
    public List<Slots> findBySectionId(Long sectionId) {
        String sql = "SELECT s.* FROM slots s JOIN courses c ON s.course_id = c.id WHERE c.section_id = ?";

        List<Slots> slots = jdbcTemplate.query(sql, rowMapper, sectionId);
        return slots;
    }

    @Override
    public Slots save(Slots newSlot) {
        String sql = "INSERT INTO slots (course_id, day, start_time, end_time) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(sql, newSlot.getCourseId(), newSlot.getDay().toString(), newSlot.getStartTime(),
                newSlot.getEndTime());

        newSlot.setId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
        return newSlot;
    }

    @Override
    public Slots update(Slots newSlot) {
        String sql = "UPDATE slots SET course_id = ?, day = ?, start_time = ?, end_time = ? WHERE id = ?";

        jdbcTemplate.update(sql, newSlot.getCourseId(), newSlot.getDay().toString(), newSlot.getStartTime(),
                newSlot.getEndTime(), newSlot.getId());

        return newSlot;
    }

    @Override
    public Optional<Slots> findById(Long id) {
        String sql = "SELECT * FROM slots WHERE id = ?";

        List<Slots> slots = jdbcTemplate.query(sql, rowMapper, id);
        if (slots.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(slots.get(0));
        }
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM slots WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
