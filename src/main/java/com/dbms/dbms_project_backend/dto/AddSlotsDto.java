package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSlotsDto {
    @NotNull(message = "courseId cannot be empty")
    private Long courseId;

    @NotNull(message = "startTime cannot be empty")
    @Min(value = 0, message = "startTime should not be less than 0")
    @Max(value = 2359, message = "startTime should not be more than 2359")
    private Integer startTime;

    @NotNull(message = "endTime cannot be empty")
    @Min(value = 0, message = "endTime should not be less than 0")
    @Max(value = 2359, message = "endTime should not be more than 2359")
    private Integer endTime;

    @NotNull(message = "day cannot be empty")
    private String day;
}
