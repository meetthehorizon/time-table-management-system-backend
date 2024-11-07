package com.dbms.dbms_project_backend.event;

import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserRoleAddedEvent extends ApplicationEvent {
  private final User user;
  private final Role role;

  private static final Logger logger = LoggerFactory.getLogger(UserRoleAddedEvent.class);

  public UserRoleAddedEvent(Object source, User user, Role role) {
    super(source);
    this.user = user;
    this.role = role;
    logger.info("[INFO] UserRoleAddedEvent created for user: {} with role: {}",
        user.getUsername(), role.name());
  }
}
