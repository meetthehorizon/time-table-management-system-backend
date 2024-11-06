package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;
import com.dbms.dbms_project_backend.model.Course;

public interface CourseRepository {

    public List<Course> findAll();

    public Optional<Course> findById(Long id);

    public Course save(Course course);

    public Course update(Course course);

    public void deleteById(Long id);

    boolean existsById(Long id);

    public boolean existsByUniqueFields(Course course);

    public Course findByUniqueFields(Course course);



    
}

