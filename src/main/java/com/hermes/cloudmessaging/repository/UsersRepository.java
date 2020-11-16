package com.hermes.cloudmessaging.repository;

import com.hermes.cloudmessaging.entity.mongo.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends MongoRepository<User, String> {

    Optional<User> findByUserId(String userId);
    boolean deleteByUserId(String userId);
}
