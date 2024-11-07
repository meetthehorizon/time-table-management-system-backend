package com.dbms.dbms_project_backend.controller;

import com.dbms.dbms_project_backend.dto.AddRoleDto;
import com.dbms.dbms_project_backend.dto.DeleteUserRoleDto;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.model.enumerations.Role;
import com.dbms.dbms_project_backend.service.LogService;
import com.dbms.dbms_project_backend.service.UserRolesService;
import jakarta.validation.Valid;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-roles")
public class UserRolesController {
  @Autowired
  private UserRolesService userRolesService;

  @Autowired
  private LogService logService;

  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping()
  public ResponseEntity<User> addRole(@Valid @RequestBody AddRoleDto addRoleDto) {
    logService.logRequestAndUser("/user-roles", "POST");

    User user = userRolesService.addRoleToUser(addRoleDto.getId(),
        addRoleDto.getRoleName());
    return ResponseEntity.ok(user);
  }

  @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
  @GetMapping("/{userId}")
  public ResponseEntity<Set<Role>> getRoles(@PathVariable Long userId) {
    logService.logRequestAndUser("/user-roles/" + userId, "GET");

    Set<Role> roles = userRolesService.getRolesForUser(userId);
    return ResponseEntity.ok(roles);
  }

  @PreAuthorize("hasRole('ADMIN')")
  @DeleteMapping()
  public ResponseEntity<User> deleteRole(@Valid @RequestBody DeleteUserRoleDto deleteUserRoleDto) {
    logService.logRequestAndUser("/user-roles", "DELETE");

    User user = userRolesService.deleteRoleFromUser(
        deleteUserRoleDto.getId(), deleteUserRoleDto.getRoleName());
    return ResponseEntity.ok(user);
  }
}
