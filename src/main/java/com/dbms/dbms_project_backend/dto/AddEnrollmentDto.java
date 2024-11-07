package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddEnrollmentDto {
  @NotNull(message = "StudentId is required")
  private Long studentId;

  @NotNull(message = "SectionId is required")
  private Long sectionId;
}
