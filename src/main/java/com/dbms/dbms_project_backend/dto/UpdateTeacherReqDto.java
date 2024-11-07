package com.dbms.dbms_project_backend.dto;

import com.dbms.dbms_project_backend.model.enumerations.Position;

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
public class UpdateTeacherReqDto {
    private Long schoolId;
    private Long subjectId;
    private Long teacherId;
    private Position position;
}
