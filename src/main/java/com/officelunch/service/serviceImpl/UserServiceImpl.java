package com.officelunch.service.serviceImpl;

import com.officelunch.model.Availability;
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
    AvailabilityRepo availabilityRepo;
    private UserRepositories userRepositories;
    private BCryptPasswordEncoder encoder;

    @Autowired
    UserServiceImpl(UserRepositories userRepositories, BCryptPasswordEncoder encoder) {
        this.userRepositories = userRepositories;
        this.encoder = encoder;
    }

    @Override
    public String saveUser(User user) {
        if (userRepositories.existsByUsername(user.getUsername())) {
            return null;
        } else {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            userRepositories.save(user);
            Availability availability = new Availability();
            availability.setId(user.getId());
            availability.setUsername(user.getUsername());
            availability.setUser(user);
            availability.setFoodPref("Not Selected");
            availabilityRepo.save(availability);

            return "registered Success";
        }
    }

    @Override
    public User getUserByUserName(String username, String password) {
        User user = userRepositories.findByUsername(username);
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
        User usr = userRepositories.findByUsername(user.getUsername());
        usr.setPassword(password);
        return userRepositories.save(usr);

    }

    @Override
    public User getUserByUserId(int userId) {
        return userRepositories.findById(userId).get();
    }


}
