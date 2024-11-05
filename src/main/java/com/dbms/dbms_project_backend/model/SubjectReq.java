package com.dbms.dbms_project_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.dbms.dbms_project_backend.model.enumerations.Position;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SubjectReq {
    private Long id;
    private Long subjectId;
    private Integer numLab;
    private Integer classLevel;
    private Integer numLecture;
    private Integer attendanceCriteria;
    private Position teacherPosition;
}
