package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public class AddSubjectReqDto {
    @NotNull(message = "School ID is required")
    private Long subjectId;

    @NotNull(message = "School ID is required")
    private Integer numLab;

    @NotNull(message = "School ID is required")
    @Min(1)
    @Max(12)
    private Integer classLevel;

    @NotNull(message = "School ID is required")
    private Integer numLecture;

    @NotNull(message = "School ID is required")
    @Min(0)
    @Max(100)
    private Integer attendanceCriteria;

    @NotNull(message = "School ID is required")
    private String teacherPosition;
}
