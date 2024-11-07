package com.dbms.dbms_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCourseDto {
    private Long sectionId;
    private Long subjectReqId;
    private Long teacherReqId;
}
