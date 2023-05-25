package com.officelunch.service;

import com.officelunch.model.User;
import com.officelunch.repositories.UserRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceTwo {

    @Autowired
    UserRepositories userRepositories;


    public User register(User user){


        return userRepositories.save(user);
    }
}
