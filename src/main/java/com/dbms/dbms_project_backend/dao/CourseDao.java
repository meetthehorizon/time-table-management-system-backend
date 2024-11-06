package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.dbms.dbms_project_backend.model.Course;
import com.dbms.dbms_project_backend.repository.CourseRepository;

@Repository
public class CourseDao implements CourseRepository {

	@Override
	public List<Course> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public Optional<Course> findById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Course save(Course course) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public Course update(Course course) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'existsById'");
	}

	@Override
	public boolean existsByUniqueFields(Course course) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'existsByUniqueFields'");
	}

	@Override
	public Course findByUniqueFields(Course course) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findByUniqueFields'");
	}

}
