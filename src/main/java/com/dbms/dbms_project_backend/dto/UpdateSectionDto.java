package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSectionDto {
    private Long schoolId;

    @Min(value = 1, message = "Class level must be at least 1")
    @Max(value = 12, message = "Class level must be at most 12")
    private Integer classLevel;

    @Pattern(regexp = "^(20\\d{2})$", message = "Running year must be a 4 digit integer from 2000")
    private String runningYear;

    @Pattern(regexp = "^[A-Z]$", message = "Section must be a capital letter from A to Z")
    private String section;
}
