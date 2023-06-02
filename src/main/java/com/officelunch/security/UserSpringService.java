package com.officelunch.security;

import com.officelunch.model.User;
import com.officelunch.repositories.UserRepositories;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSpringService implements UserDetailsService {

    @Autowired
    UserRepositories userRepositories;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepositories.getByUsername(username);

        return user.map(UserSpringDetails::new)
                .orElseThrow(()-> new UsernameNotFoundException(username));
    }
}
