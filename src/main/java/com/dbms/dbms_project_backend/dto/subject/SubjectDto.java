package com.dbms.dbms_project_backend.dto.subject;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {
    @NotNull(message = "id cannot be empty")
    private Long id;
    @NotNull(message = "subject name cannot be empty")
    private String Name;
    @NotNull(message = "subject code cannot be empty")
    private String Code;

}
