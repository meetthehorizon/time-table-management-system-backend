package com.dbms.dbms_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEnrollmentDto {
    private Long studentId;
    private Long sectionId;
}
