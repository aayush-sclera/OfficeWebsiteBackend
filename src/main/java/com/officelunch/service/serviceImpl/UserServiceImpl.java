package com.officelunch.service.serviceImpl;

import com.officelunch.model.User;
import com.officelunch.repositories.AvailabilityRepo;
import com.officelunch.repositories.UserRepositories;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AvailabilityRepo availabilityRepo;

    @Autowired
    private UserRepositories userRepositories;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Autowired
    UserServiceImpl(UserRepositories userRepositories, BCryptPasswordEncoder encoder) {
        this.userRepositories = userRepositories;
        this.encoder = encoder;
    }

    @Override
    public String saveUser(User user) {

        String password = encoder.encode(user.getPassword());
        user.setUsername(user.getUsername().toLowerCase());
        user.setPassword(password);
        userRepositories.save(user);
        return "registered Success";

    }

    @Override
    public User getUserByUserName(String username, String password) {
        User user = userRepositories.findByUsername(username.toLowerCase());
        System.out.println(user.getPassword());
        if (user == null) {
            System.out.println("Invalid Username and Password");
            return null;
        } else {
            if (encoder.matches(password, user.getPassword())) {
                return user;
            }
            return null;
        }
    }

    @Override
    public User resetUserPassword(User user) {
        String password = encoder.encode(user.getPassword());
        User usr = userRepositories.findByUsername(user.getUsername().toLowerCase());
        usr.setPassword(password);
        usr.setStat(true);
        return userRepositories.save(usr);
    }

    @Override
    public User getUserByUserId(int userId) {
        return userRepositories.findById(userId).get();
    }

    @Override
    public User changeUserPassword(String pass, User user) {
        user.setPassword(encoder.encode(pass));
        return userRepositories.save(user);
    }

    @Override
    public String deactivateEmployee(String email) {
        userRepositories.deactivateEmployee(email);
        return "Employee Deactivated Successfully";
    }


}
