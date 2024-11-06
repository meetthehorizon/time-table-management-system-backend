package com.dbms.dbms_project_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.Year;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString
public class Section {
    private Long id;
    private Long schoolId;
    private Byte classLevel;
    private Year runningYear;
    private Long classTeacherId;
    private String section;
}
