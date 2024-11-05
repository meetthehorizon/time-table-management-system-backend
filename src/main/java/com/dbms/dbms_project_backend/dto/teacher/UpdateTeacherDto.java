package com.dbms.dbms_project_backend.dto.teacher;

import com.dbms.dbms_project_backend.dto.authentication.UpdateUserDto;

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
    private UpdateUserDto user;
    private String position;
}
