package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddAttendanceDto {

    @NotNull(message = "Student ID is required.")
    private Long studentId;

    @NotNull(message = "Slot ID is required.")
    private Long slotId;

    @NotNull(message = "Status is required.")
    @Size(min = 1, max = 20, message = "Status must be between 1 and 20 characters.")
    private String remark;  // Example: "Present", "Absent", "Late"

    
}
