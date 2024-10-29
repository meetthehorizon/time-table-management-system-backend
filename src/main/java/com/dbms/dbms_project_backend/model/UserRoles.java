package com.dbms.dbms_project_backend.model;

import com.dbms.dbms_project_backend.model.enumerations.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)

public class UserRoles {
    private Long userRoleId; 
    private Long userId; 
    private Role roleName; 

    public User toUser(User user) {
        user.addRole(this.roleName);
        return user;
    }
}
