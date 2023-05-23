package com.officelunch.controller;

import com.officelunch.model.User;
import com.officelunch.service.UserServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class LoginController {

    @Autowired
    UserServiceTwo userServiceTwo;
    @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody User user){
            User user1 = new User();
            user1.setUsername(user.getUsername());
            user1.setPassword(user.getPassword());
            user1.setEmail(user.getEmail());

            ResponseEntity<?> response = new ResponseEntity<>(userServiceTwo.register(user1), HttpStatus.OK);
            return response;

        }
}
