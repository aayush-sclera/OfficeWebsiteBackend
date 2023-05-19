package com.officelunch.controller;

import com.officelunch.helper.ConsequentDaysValidator;
import com.officelunch.model.Availability;
import com.officelunch.model.User;
import com.officelunch.repositories.UserRepositories;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("/officeLunch/employees")
public class UserController {

    @Autowired
    UserRepositories userRepositories;
    @Autowired
    private UserService userService;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private ConsequentDaysValidator validator;

    @PostMapping("/register")
    public ResponseEntity<?> postUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user,HttpSession session) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity<>("Username or Password is Empty", HttpStatus.EXPECTATION_FAILED);
        }
        User userData = userService.getUserByUserName(user.getUsername(), user.getPassword());
        if (userData == null) {
            return new ResponseEntity<>("username or password is invalid", HttpStatus.BAD_GATEWAY);
        } else {
            session.setAttribute("username",user.getUsername());
            return new ResponseEntity<>("User login Successfully", HttpStatus.OK);
        }
    }


    @PostMapping("/enroll")
    public ResponseEntity<?> enrollForFood(@RequestBody Availability availability,HttpSession session) {
        String ss = (String) session.getAttribute("username");
        String name = availability.getUsername();
        System.out.println(name);
        User user =userRepositories.findByUsername(availability.getUsername());
        if(user != null && ss.equals(user.getUsername())){
            availability.setUser(user);
            availability.setAttendance("Present");
            return ResponseEntity.ok().body(availabilityService.saveEmployeeStatus(availability, user.getId()));

        }else {
            return ResponseEntity.badRequest().body(name+" is in home .");
        }
    }

    @PostMapping("/pwreset")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.resetUserPassword(user), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session =request.getSession();
        session.invalidate();
        return "user logged out successfully";
    }


}
