package com.dbms.dbms_project_backend.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeacherDto {
    private Long subjectId;
    @Valid
    private UpdateUserDto user;
    private String position;
}
