package com.dbms.dbms_project_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import com.dbms.dbms_project_backend.model.enumerations.Day;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Slots {
    private Long id;
    private Long courseId;
    private Integer startTime;
    private Integer endTime;
    private Day day;
}
