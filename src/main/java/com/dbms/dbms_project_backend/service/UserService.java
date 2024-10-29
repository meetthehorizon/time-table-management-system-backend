package com.dbms.dbms_project_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dbms.dbms_project_backend.dto.RegisterUserDto;
import com.dbms.dbms_project_backend.model.User;
import com.dbms.dbms_project_backend.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setName(user.getName());
        registerUserDto.setEmail(user.getEmail());
        registerUserDto.setPhone(user.getPhone());
        registerUserDto.setAddress(user.getAddress());
        registerUserDto.setPassword(user.getPassword());

        user = authenticationService.signup(registerUserDto);
        return user;
    }

    public User updateUser(User user) {
        return userRepository.update(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
