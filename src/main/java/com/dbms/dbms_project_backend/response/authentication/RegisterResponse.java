package com.dbms.dbms_project_backend.response.authentication;

import com.dbms.dbms_project_backend.model.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RegisterResponse {
    private User user;
}
