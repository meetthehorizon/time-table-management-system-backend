package com.dbms.dbms_project_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.dbms.dbms_project_backend.model.enumerations.Position;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Teacher {
    public Long id;
    public User user;
    public Position Position;
    public Long subjectId;
    public Long schoolId; // TODO - add schoolId
}
