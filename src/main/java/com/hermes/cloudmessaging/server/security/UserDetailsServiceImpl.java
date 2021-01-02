package com.hermes.cloudmessaging.server.security;

import com.hermes.cloudmessaging.model.constants.enums.UserRoles;
import com.hermes.cloudmessaging.model.dto.request.UserCreationRequest;
import com.hermes.cloudmessaging.model.dto.request.UserCreationResponse;
import com.hermes.cloudmessaging.model.entity.mongo.User;
import com.hermes.cloudmessaging.service.DbCRUDService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("UserDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DbCRUDService<User, UserCreationRequest, UserCreationResponse, String> userCrudService;

    public UserDetailsServiceImpl(@Qualifier("UserCrudService") DbCRUDService<User, UserCreationRequest,
            UserCreationResponse, String> userCrudService) {
        this.userCrudService = userCrudService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCreationResponse user = userCrudService.find(new UserCreationRequest(username, null, UserRoles.ADMIN)).get(0);
        return new HermesUserDetails(user);
    }
}
