package com.dbms.dbms_project_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    @Size(min = 3, message = "Name must be at least 3 characters long")
    @NotNull(message = "Name is required")
    private String name;

    @Pattern(regexp = "^[\\w.+\\-]+@iitbhu\\.ac\\.in$", message = "Email must end with @iitbhu.ac.in")
    @NotNull(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits long")
    @NotNull(message = "Phone number is required")
    private String phone;

    @NotNull(message = "Address is required")
    private String address;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=]).*$", message = "Password must contain at least one digit, one uppercase letter, one lowercase letter, and one special character")
    @NotNull(message = "Password is required")
    private String password;
}
