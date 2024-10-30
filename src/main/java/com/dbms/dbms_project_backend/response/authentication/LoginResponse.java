package com.dbms.dbms_project_backend.response.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginResponse {
    private String token;
    private long expiresIn;
    private boolean success;
}
