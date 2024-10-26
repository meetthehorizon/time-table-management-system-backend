package com.dbms.dbms_project_backend.event.user_roles;

import org.springframework.context.ApplicationEvent;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;

import lombok.Getter;

@Getter
public class UserRoleAddedEvent extends ApplicationEvent {
    private final User user;
    private final Role role;

    public UserRoleAddedEvent(Object source, User user, Role role) {
        super(source);
        this.user = user;
        this.role = role;
    }

}
