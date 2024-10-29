package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;

public interface UserRepository {
    public Optional<User> findById(Long id);

    public List<User> findAll();

    public User save(User user);

    public User update(User user);

    public void deleteById(Long id);

    List<User> findUsersByRole(Role role);

    Optional<User> findByEmail(String email);
}
