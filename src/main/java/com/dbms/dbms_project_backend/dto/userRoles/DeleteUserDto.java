package com.dbms.dbms_project_backend.dto.userRoles;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserDto {
    private Long id;
    private String roleName;
}
