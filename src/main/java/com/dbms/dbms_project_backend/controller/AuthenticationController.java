package com.dbms.dbms_project_backend.controller;

import com.dbms.dbms_project_backend.dto.LoginUserDto;
import com.dbms.dbms_project_backend.dto.RegisterUserDto;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.response.authentication.LoginResponse;
import com.dbms.dbms_project_backend.response.authentication.RegisterResponse;
import com.dbms.dbms_project_backend.service.AuthenticationService;
import com.dbms.dbms_project_backend.service.JwtService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationService authenticationService;

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

  @PostMapping("/signup")
  public ResponseEntity<RegisterResponse> register(
      @Valid @RequestBody RegisterUserDto registerUserDto) {
    logger.info("[INFO] Registering user with email: {}",
        registerUserDto.getEmail());
    User registeredUser = authenticationService.signup(registerUserDto);
    RegisterResponse registerResponse = new RegisterResponse().setUser(registeredUser);
    logger.info("[INFO] User registered successfully with email: {}",
        registerUserDto.getEmail());

    return ResponseEntity.ok(registerResponse);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
    logger.info("[INFO] Authenticating user with email: {}",
        loginUserDto.getEmail());
    User authenticatedUser = authenticationService.authenticate(loginUserDto);
    logger.debug("[DEBUG] User authenticated successfully: {}",
        authenticatedUser);

    String jwtToken = jwtService.generateToken(authenticatedUser);
    logger.debug("[DEBUG] JWT token generated: {}", jwtToken);
    LoginResponse loginResponse = new LoginResponse()
        .setToken(jwtToken)
        .setExpiresIn(jwtService.getExpirationTime())
        .setSuccess(true)
        .setUserid(authenticatedUser.getId());

    logger.info("[INFO] User logged in successfully with email: {}",
        loginUserDto.getEmail());
    return ResponseEntity.ok(loginResponse);
  }
}
