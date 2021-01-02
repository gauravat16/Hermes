package com.hermes.cloudmessaging.server.controller;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.dto.request.UserCreationResponse;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.server.exception.BaseRuntimeException;
import com.hermes.cloudmessaging.server.security.HermesUserDetails;
import com.hermes.cloudmessaging.service.DbCRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final DbCRUDService<User, UserCreationRequest, UserCreationResponse, String> userCrudService;

    public UserController(@Qualifier("UserCrudService") DbCRUDService<User, UserCreationRequest,
            UserCreationResponse, String> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @PostMapping("/register")
    private ResponseEntity<UserCreationResponse> registerUser(@RequestBody UserCreationRequest userCreationRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAnonSession = auth instanceof AnonymousAuthenticationToken;
        switch (userCreationRequest.getUserRole()) {
            case USER:
                if (auth instanceof AnonymousAuthenticationToken) {
                    throw new BaseRuntimeException("User can't be registered when session is unauthenticated",
                            HttpStatus.BAD_REQUEST, true);
                }
                return ResponseEntity.ok().body(userCrudService.create(userCreationRequest));
            case ADMIN:
                if ((isAnonSession && !isAdminRegistered()) || (!isAnonSession
                        && ((HermesUserDetails) auth.getPrincipal()).getUser().getRole().equals(UserRoles.ADMIN))) {
                    return ResponseEntity.ok().body(userCrudService.create(userCreationRequest));
                } else {
                    throw new BaseRuntimeException("Admin is already registered! Please register another admin via swagger.",
                            HttpStatus.FORBIDDEN, true);
                }

        }

        throw new BaseRuntimeException("User can't be registered", HttpStatus.BAD_REQUEST, true);
    }

    private boolean isAdminRegistered() {
        try {
            List<UserCreationResponse> userCreationResponses =
                    userCrudService.find(new UserCreationRequest(null, null, UserRoles.ADMIN));
            return null != userCreationResponses && !userCreationResponses.isEmpty();
        } catch (Exception e) {
            log.trace("No admin found");
            return false;
        }

    }

    @GetMapping("/logout")
    private ResponseEntity<String> logout() {
        return ResponseEntity.ok().body("Logged Out");
    }

}
