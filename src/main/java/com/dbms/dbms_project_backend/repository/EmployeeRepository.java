package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.Employee;

public interface EmployeeRepository {
    public List<Employee> findAll();

    public Optional<Employee> findById(Long id);

    public Employee update(Employee employee);
}
