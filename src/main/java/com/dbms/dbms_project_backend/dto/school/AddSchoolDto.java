package com.dbms.dbms_project_backend.dto.school;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddSchoolDto {
    String name;
    String address;
    String phone;
    String email;
}
