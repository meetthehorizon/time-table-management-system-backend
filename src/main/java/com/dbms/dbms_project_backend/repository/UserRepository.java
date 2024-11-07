package com.dbms.dbms_project_backend.repository;

import java.util.List;
import java.util.Optional;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;

public interface UserRepository {
    public Optional<User> findById(Long id);

    public List<User> findAll();

    public List<User> findAllBySchoolId(Long id);

    public User save(User user);

    public User update(User user);

    public void deleteById(Long id);

    Optional<User> findByEmail(String email);

    public boolean existsByEmail(String email);

    public boolean existsByPhone(String phone);

    public boolean existsById(Long id);

    public List<User> findAllByRole(Role roleStudent);
}
