package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.dto.request.UserCreationResponse;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.server.exception.BaseRuntimeException;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service("UserCrudService")
public class UserCrudService implements DbCRUDService<User, UserCreationRequest, UserCreationResponse, String> {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserCrudService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User mapRequestToEntity(UserCreationRequest request) {
        Assert.notNull(request, "request is empty");
        Assert.notNull(request.getUserId(), "user id is null");
        Assert.notNull(request.getPasswordHash(), "password is null");
        return new User(request.getUserId(), request.getPasswordHash(), request.getUserRole());
    }

    @Override
    public UserCreationResponse mapEntityToResponse(User entity) {
        return new UserCreationResponse(entity.getUserId(), entity.getRole(), entity.getPasswordHash());
    }

    @Override
    public UserCreationResponse create(UserCreationRequest user) {
        Assert.notNull(user, "User request is null");
        Assert.notNull(user.getPasswordHash(), "password is null");
        Assert.notNull(user.getUserId(), "User id is null");
        Assert.notNull(user.getUserRole(), "User role is null");

        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        try {
            return mapEntityToResponse(usersRepository.save(mapRequestToEntity(user)));
        } catch (Exception e) {
            throw new BaseRuntimeException("User already exists", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<UserCreationResponse> read(UserCreationRequest user) {
        Assert.notNull(user, "request is empty");

        if(user.getUserId() != null){
            return usersRepository.findByUserId(user.getUserId()).map(this::mapEntityToResponse)
                    .map(Collections::singletonList)
                    .orElseThrow(() -> new BaseRuntimeException("User not found", HttpStatus.NOT_FOUND, true));
        }

        if(user.getUserRole() != null){
            return usersRepository.findByRole(user.getUserRole()).map(this::mapEntityToResponse)
                    .map(Collections::singletonList)
                    .orElseThrow(() -> new BaseRuntimeException("User not found", HttpStatus.NOT_FOUND, true));
        }

        throw new BaseRuntimeException("Invalid request", HttpStatus.BAD_REQUEST, true);
    }

    @Override
    public UserCreationResponse update(UserCreationRequest user) {
        User user1 = mapRequestToEntity(user);
        return mapEntityToResponse(usersRepository.save(user1));
    }

    @Override
    public List<UserCreationResponse> delete(UserCreationRequest user) {
        List<UserCreationResponse> dbUsers = read(user);
        return dbUsers.stream().filter(u -> usersRepository.deleteByUserId(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserCreationResponse> find(UserCreationRequest user) {
        return read(user);
    }


}
