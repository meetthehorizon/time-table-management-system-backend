package com.dbms.dbms_project_backend.dto.user;

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
public class UpdateUserDto {
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @Pattern(regexp = "^[\\w.+\\-]+@iitbhu\\.ac\\.in$", message = "Email must end with @iitbhu.ac.in")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits long")
    private String phone;

    private String address;
}
