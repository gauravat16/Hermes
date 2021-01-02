package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.service.DbCRUDService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final DbCRUDService<User, UserCreationRequest, User, String> userCrudService;

    public UserController(@Qualifier("UserCrudService") DbCRUDService<User, UserCreationRequest,
            User, String> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PostMapping("/register")
    private ResponseEntity<User> registerUser(@RequestBody UserCreationRequest userCreationRequest) {
        return ResponseEntity.ok().body(userCrudService.create(userCreationRequest));
    }

    @GetMapping("/logout")
    private ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("Logged Out");
    }

}
