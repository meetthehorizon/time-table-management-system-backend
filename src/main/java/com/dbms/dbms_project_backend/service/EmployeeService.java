package com.dbms.dbms_project_backend.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.exception.NotFoundException;
import com.dbms.dbms_project_backend.model.Employee;
import com.dbms.dbms_project_backend.repository.EmployeeRepository;
import com.dbms.dbms_project_backend.repository.SchoolRepository;
import com.dbms.dbms_project_backend.repository.UserRepository;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    public List<Employee> findAll() {
        logger.info("[INFO] Fetching all employees");

        List<Employee> employees = employeeRepository.findAll();
        logger.debug("[DEBUG] Fetched all employees: {}", employees);

        return employees;
    }

    public Employee findById(Long id) {
        logger.info("[INFO] Fetching employee with id: {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Employee", "id", id));
        logger.debug("[DEBUG] Fetched employee: {}", employee);

        return employee;
    }

    public Employee update(Employee employee) {
        logger.info("[INFO] Updating employee with id: {}", employee.getId());

        if (employee.getId() != null && !userRepository.existsById(employee.getId())) {
            throw new NotFoundException("User", "id", employee.getId());
        }

        if (employee.getSchoolId() != null && !schoolRepository.existsById(employee.getSchoolId())) {
            throw new NotFoundException("School", "id", employee.getSchoolId());
        }

        Employee updatedEmployee = employeeRepository.update(employee);
        logger.debug("[DEBUG] Updated employee: {}", updatedEmployee);

        return updatedEmployee;
    }
}
