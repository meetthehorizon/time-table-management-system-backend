package com.dbms.dbms_project_backend.dto;

import java.time.Year;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSectionDto {
    @NotNull(message = "School id is required")
    private Long schoolId;

    @NotNull(message = "Class level is required")
    @Pattern(regexp = "^(1[0-2]|[1-9])$", message = "Class level must be between 1 and 12")
    private Byte classLevel;

    @NotNull(message = "Running year is required")
    @Pattern(regexp = "^(20\\d{2})$", message = "Running year must be a 4 digit integer from 2000")
    private Year runningYear;

    private Long classTeacherId;

    @NotNull(message = "Section is required")
    @Pattern(regexp = "^[A-Z]$", message = "Section must be a capital letter from A to Z")
    private Character section;
}
