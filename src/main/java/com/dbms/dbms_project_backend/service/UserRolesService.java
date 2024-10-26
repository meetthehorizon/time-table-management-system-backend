package com.dbms.dbms_project_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.repository.UserRepository;
import com.dbms.dbms_project_backend.repository.UserRolesRepository;

@Service
public class UserRolesService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    public User addRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).get();
        Role role = Role.valueOf(roleName);

        user = userRolesRepository.addRoleByUser(user, role);
        return user;
    }
}
