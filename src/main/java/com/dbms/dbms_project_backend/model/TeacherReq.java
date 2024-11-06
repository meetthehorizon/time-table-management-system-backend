package com.dbms.dbms_project_backend.model;

import com.dbms.dbms_project_backend.model.enumerations.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TeacherReq {
    private Long id;
    private Long schoolId;
    private Long subjectId;
    private Long teacherId;
    private Position position;
}
