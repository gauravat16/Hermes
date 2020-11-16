package com.hermes.cloudmessaging.controller;

import com.hermes.cloudmessaging.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.entity.mongo.User;
import com.hermes.cloudmessaging.service.impl.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserCrudService userCrudService;

    @PostMapping("/register")
    private ResponseEntity<User> registerUser(@RequestBody UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok().body(userCrudService.create(userCreationRequest));
    }

}
