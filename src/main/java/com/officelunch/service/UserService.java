package com.officelunch.service;

import com.officelunch.model.User;

public interface UserService {
    String  saveUser(User user);
    User getUserByUserName(String username,String password);
    User resetUserPassword(User user);
    User getUserByUserId(int userId);
}
