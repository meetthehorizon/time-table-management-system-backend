package com.dbms.dbms_project_backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSchoolDto {
  @Size(min = 3, message = "School Name must be at least 3 characters long")
  @NotNull(message = "School Name can not be empty")
  String name;

  @NotNull(message = "Address can not be empty")
  String address;

  @NotNull(message = "Phone can not be empty")
  @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits long")
  String phone;

  @Pattern(regexp = "^[\\w.+\\-]+@iitbhu\\.ac\\.in$", message = "Email must end with @iitbhu.ac.in")
  @NotNull(message = "Email can not be empty")
  String email;
}
