package com.officelunch.service;

import com.officelunch.model.User;

import java.security.Principal;

public interface UserService {
    String  saveUser(User user);
    User getUserByUserName(String username,String password);
    User resetUserPassword(User user);
    User getUserByUserId(int userId);
    User changeUserPassword(String pass, User user);
    String deactivateEmployee(String email);

}
