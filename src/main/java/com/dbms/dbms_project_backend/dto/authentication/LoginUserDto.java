package com.dbms.dbms_project_backend.dto.authentication;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    @Pattern(regexp = "^[\\w.+\\-]+@xyz\\.com$", message = "Email must end with @iitbhu.ac.in")
    @NotNull(message = "Email can not be empty")
    private String email;

    @NotNull
    private String password;
}
