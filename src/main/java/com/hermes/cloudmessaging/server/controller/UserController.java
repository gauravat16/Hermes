package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.database.entity.mongo.User;
import com.hermes.cloudmessaging.core.impl.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/logout")
    private ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("Logged Out");
    }

}
