package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddTeacherReqDto {
    @NotNull(message = "School is required")
    private Long schoolId;

    @NotNull(message = "Subject is required")
    private Long subjectId;

    @NotNull(message = "Position is required")
    private String position;

    private Long teacherId;
}
