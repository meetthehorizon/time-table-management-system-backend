package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRoleDto {
    @NotNull(message = "User ID is required")
    private Long id;

    @NotNull(message = "Role name is required")
    private String roleName;
}
