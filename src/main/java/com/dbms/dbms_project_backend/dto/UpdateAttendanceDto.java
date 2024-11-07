package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAttendanceDto {

    @Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters.")
    private String remark; 

    
}
