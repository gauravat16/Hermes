package com.hermes.cloudmessaging.service.repository;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);

    Optional<User> findByRole(UserRoles userRole);

    boolean deleteByUserId(String userId);
}
