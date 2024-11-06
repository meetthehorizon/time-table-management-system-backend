package com.dbms.dbms_project_backend.model;

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
public class Course {

    private Long id; 
    private Long sectionId; 
    private Long subjectReqId; 
    private Long teacherReqId; 
}
