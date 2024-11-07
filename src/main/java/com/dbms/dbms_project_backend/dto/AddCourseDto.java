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
public class AddCourseDto {
    @NotNull(message = "Section ID is required")
    private Long sectionId;

    @NotNull(message = "Subject Requirement ID is required")
    private Long subjectReqId;

    private Long teacherReqId;
}
