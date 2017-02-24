package com.cats.services.user;

import com.cats.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * Created by andrey on 23.02.17.
 */
public interface UserService extends UserDetailsService {

    UserDetails loadUserByUsername(String username);

    User save(User user);

    List<User> findAll();

    void updateTokenByUsername(String username, String token);
}
