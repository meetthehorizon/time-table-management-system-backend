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
    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @NotNull(message = "numLab is required")
    private Integer numLab;

    @NotNull(message = "classLevel should be between 1 and 12")
    @Min(1)
    @Max(12)
    private Integer classLevel;

    @NotNull(message = "numLeacture is required")
    private Integer numLecture;

    @NotNull(message = "attendanceCriteria should be between 0 and 100")
    @Min(0)
    @Max(100)
    private Integer attendanceCriteria;

    @NotNull(message = "teacherPosition is required")
    private String teacherPosition;
}
