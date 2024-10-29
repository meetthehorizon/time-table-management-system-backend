package com.dbms.dbms_project_backend.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String password;
}
