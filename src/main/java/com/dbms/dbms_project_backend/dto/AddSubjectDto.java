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
public class AddSubjectDto {
  @NotNull(message = "subject name cannot be empty")
  private String name;

  @NotNull(message = "subject code cannot be empty")
  private String code;
}
