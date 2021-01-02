package com.hermes.cloudmessaging.core.impl;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.server.exception.BaseRuntimeException;
import com.hermes.cloudmessaging.service.DbCRUDService;
import com.hermes.cloudmessaging.service.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service("UserCrudService")
public class UserCrudService implements DbCRUDService<User, UserCreationRequest, User, String> {

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
        return new User(request.getUserId(), request.getPasswordHash(), UserRoles.USER);
    }

    @Override
    public User mapEntityToResponse(User entity) {
        return entity;
    }

    @Override
    public User create(UserCreationRequest user) {
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        try {
            return usersRepository.save(mapRequestToEntity(user));
        } catch (Exception e) {
            throw new BaseRuntimeException("User already exists", HttpStatus.BAD_REQUEST);
        }

    }

    @Override
    public List<User> read(UserCreationRequest user) {
        Assert.notNull(user, "request is empty");
        Assert.notNull(user.getUserId(), "user id is null");

        return usersRepository.findByUserId(user.getUserId()).map(Collections::singletonList)
                .orElseThrow(() -> new BaseRuntimeException("User not found", HttpStatus.NOT_FOUND, true));
    }

    @Override
    public User update(UserCreationRequest user) {
        User user1 = mapRequestToEntity(user);
        return usersRepository.save(user1);
    }

    @Override
    public List<User> delete(UserCreationRequest user) {
        List<User> dbUsers = read(user);
        return dbUsers.stream().filter(u -> usersRepository.deleteByUserId(user.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> find(UserCreationRequest user) {
        return read(user);
    }
}
