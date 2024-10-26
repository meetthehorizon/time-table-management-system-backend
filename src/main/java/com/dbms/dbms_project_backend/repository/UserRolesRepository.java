package com.dbms.dbms_project_backend.repository;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;

import java.util.Set;

public interface UserRolesRepository {
    User setRolesByUser(User user);

    Set<Role> getRolesByUser(User user);

    User addRoleByUser(User user, Role role);

    User deleteRoleByUser(User user, Role role);
}
