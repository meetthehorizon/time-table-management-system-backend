package com.dbms.dbms_project_backend.dto.authentication;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @NotNull(message = "Name can not be empty")
    private String name;

    @Pattern(regexp = "^[\\w.+\\-]+@iitbhu\\.ac\\.in$", message = "Email must end with @iitbhu.ac.in")
    @NotNull(message = "Email can not be empty")
    private String email;

    @NotNull(message = "Phone can not be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits long")
    private String phone;

    @NotNull(message = "Address can not be empty")
    private String address;

    @NotNull(message = "Password can not be empty")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character")
    private String password;
}
