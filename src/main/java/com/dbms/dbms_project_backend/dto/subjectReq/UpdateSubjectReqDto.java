package com.dbms.dbms_project_backend.dto.subjectReq;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class UpdateSubjectReqDto {
    private Long subjectId;

    private Integer numLab;

    @Min(1)
    @Max(12)
    private Integer classLevel;

    private Integer numLecture;

    @Min(0)
    @Max(100)
    private Integer attendanceCriteria;

    private String teacherPosition;
}
