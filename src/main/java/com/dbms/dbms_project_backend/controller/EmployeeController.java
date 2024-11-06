package com.dbms.dbms_project_backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbms.dbms_project_backend.dto.UpdateEmployeeDto;
import com.dbms.dbms_project_backend.model.Employee;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.service.EmployeeService;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private LogService logService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @GetMapping
    public ResponseEntity<List<Employee>> findAll() {
        logService.logRequestAndUser("/employee", "GET");

        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(employees);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER') or (hasRole('ROLE_EMPLOYEE') and authentication.principal.id == #id)")
    @GetMapping("/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Long id) {
        logService.logRequestAndUser("/employee/{id}", "GET");

        Employee employee = employeeService.findById(id);
        return ResponseEntity.ok(employee);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_GENERAL_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeDto updateEmployeeDto) {
        logService.logRequestAndUser("/employee/{id}", "PUT");

        if (updateEmployeeDto.getUser() != null) {
            User existingUser = userService.findById(id);

            Optional.ofNullable(updateEmployeeDto.getUser().getName())
                    .ifPresent(existingUser::setName);

            Optional.ofNullable(updateEmployeeDto.getUser().getEmail())
                    .ifPresent(existingUser::setEmail);

            Optional.ofNullable(updateEmployeeDto.getUser().getPhone())
                    .ifPresent(existingUser::setPhone);

            Optional.ofNullable(updateEmployeeDto.getUser().getAddress())
                    .ifPresent(existingUser::setAddress);

            userService.updateUser(existingUser);
        }

        Employee existingEmployee = employeeService.findById(id);
        Optional.ofNullable(updateEmployeeDto.getSchoolId()).ifPresent(existingEmployee::setSchoolId);

        Employee updatedEmployee = employeeService.update(existingEmployee);
        return ResponseEntity.ok(updatedEmployee);
    }
}
