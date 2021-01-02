package com.hermes.cloudmessaging.security;

import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.service.DbCRUDService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DbCRUDService<User, UserCreationRequest, User, String> userCrudService;

    public UserDetailsServiceImpl(@Qualifier("UserCrudService") DbCRUDService<User, UserCreationRequest,
            User, String> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userCrudService.find(new UserCreationRequest(username, null)).get(0);
        return new HermesUserDetails(user);
    }
}
