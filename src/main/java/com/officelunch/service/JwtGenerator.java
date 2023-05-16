package com.officelunch.service;

import com.officelunch.model.User;

import java.util.Map;

public interface JwtGenerator {
    Map<String,String> generateToken(User user);
}
