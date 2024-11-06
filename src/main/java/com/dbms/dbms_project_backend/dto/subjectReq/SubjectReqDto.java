package com.dbms.dbms_project_backend.dto.subjectReq;

import com.dbms.dbms_project_backend.model.enumerations.Position;

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
public class SubjectReqDto {
    @NotNull
    private Long subjectId;

    @NotNull
    private Integer numLab;

    @NotNull
    @Min(1)
    @Max(12)
    private Integer classLevel;

    @NotNull
    private Integer numLecture;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer attendanceCriteria;

    @NotNull
    private Position teacherPosition;
}
