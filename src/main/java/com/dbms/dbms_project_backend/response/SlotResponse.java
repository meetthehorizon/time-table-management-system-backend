package com.dbms.dbms_project_backend.response;

import com.dbms.dbms_project_backend.model.Slots;
import com.dbms.dbms_project_backend.model.Subject;
import com.dbms.dbms_project_backend.model.TeacherReq;

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
public class SlotResponse {
    private Slots slots;
    private TeacherReq teacherReq;
    private Subject subject;
}
