package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.repository.CourseRepository;

@Repository
public class CourseDao implements CourseRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static RowMapper<Course> rowMapper = (rs, rowNum) -> {
		return new Course().setId(rs.getLong("id")).setSectionId(rs.getLong("section_id"))
				.setSubjectReqId(rs.getLong("subject_req_id")).setTeacherReqId(rs.getLong("teacher_req_id"));
	};

	@Override
	public List<Course> findAll() {
		String sql = "SELECT * FROM courses";
		List<Course> courses = jdbcTemplate.query(sql, rowMapper);
		return courses;
	}

	@Override
	public Optional<Course> findById(Long id) {
		String sql = "SELECT * FROM courses WHERE id = ?";
		List<Course> course = jdbcTemplate.query(sql, rowMapper, id);

		if (course.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(course.get(0));
		}
	}

	@Override
	public Course save(Course course) {
		String sql = "INSERT INTO courses (section_id, subject_req_id, teacher_req_id) VALUES (?, ?, ?)";
		jdbcTemplate.update(sql, course.getSectionId(), course.getSubjectReqId(), course.getTeacherReqId());

		course.setId(jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class));
		return course;
	}

	@Override
	public Course update(Course course) {
		String sql = "UPDATE courses SET section_id = ?, subject_req_id = ?, teacher_req_id = ? WHERE id = ?";
		jdbcTemplate.update(sql, course.getSectionId(), course.getSubjectReqId(), course.getTeacherReqId(),
				course.getId());
		return course;
	}

	@Override
	public void deleteById(Long id) {
		String sql = "DELETE FROM courses WHERE id = ?";
		jdbcTemplate.update(sql, id);
	}

	@Override
	public boolean existsById(Long id) {
		String sql = "SELECT COUNT(*) FROM courses WHERE id = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
		return count > 0;
	}

	@Override
	public boolean existsByUniqueFields(Course course) {
		String sql = "SELECT COUNT(*) FROM courses WHERE section_id = ? AND subject_req_id = ?";
		int count = jdbcTemplate.queryForObject(sql, Integer.class, course.getSectionId(), course.getSubjectReqId());

		return count > 0;
	}

	@Override
	public Course findByUniqueFields(Course course) {
		String sql = "SELECT * FROM courses WHERE section_id = ? AND subject_req_id = ?";

		Course existingCourse = jdbcTemplate.queryForObject(sql, rowMapper, course.getSectionId(),
				course.getSubjectReqId());
		return existingCourse;
	}

	@Override
	public List<Course> findBySchoolId(Long id) {
		String sql = "SELECT * FROM courses WHERE section_id IN (SELECT id FROM sections WHERE school_id = ?)";
		List<Course> courses = jdbcTemplate.query(sql, rowMapper, id);
		return courses;
	}
}
