package com.hermes.cloudmessaging.security;

import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.database.entity.mongo.User;
import com.hermes.cloudmessaging.core.impl.UserCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserCrudService userCrudService;

    @Autowired
    public UserDetailsServiceImpl(UserCrudService userCrudService) {
        this.userCrudService = userCrudService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userCrudService.find(new UserCreationRequest(username, null)).get(0);
        return new HermesUserDetails(user);
    }
}
