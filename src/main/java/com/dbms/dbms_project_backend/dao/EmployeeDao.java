package com.dbms.dbms_project_backend.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dbms.dbms_project_backend.model.Employee;
import com.dbms.dbms_project_backend.repository.EmployeeRepository;
import com.dbms.dbms_project_backend.repository.UserRepository;

@Repository
public class EmployeeDao implements EmployeeRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    private static RowMapper<Employee> rowMapper = (rs, rowNum) -> {
        Employee employee = new Employee();
        employee.setId(rs.getLong("id"));
        employee.setSchoolId(rs.getLong("school_id"));
        return employee;
    };

    @Override
    public List<Employee> findAll() {
        String sql = "SELECT * FROM employee";
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper);

        employees.forEach((employee) -> employee.setUser(userRepository.findById(employee.getId()).get()));
        return employees;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        String sql = "SELECT * FROM employee WHERE id = ?";
        List<Employee> employees = jdbcTemplate.query(sql, rowMapper, id);

        if (employees.isEmpty()) {
            return Optional.empty();
        }

        Employee employee = employees.get(0);
        employee.setUser(userRepository.findById(employee.getId()).get());

        return Optional.of(employee);
    }

    @Override
    public Employee update(Employee employee) {
        String sql = "UPDATE employee SET school_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, employee.getSchoolId() == 0 ? null : employee.getSchoolId(), employee.getId());

        return employee;
    }
}
