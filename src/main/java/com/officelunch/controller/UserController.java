package com.officelunch.controller;

import com.officelunch.helper.ConsequentDaysValidator;
import com.officelunch.model.Availability;
import com.officelunch.model.User;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.JwtGenerator;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/officeLunch/employees")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtGenerator jwtGenerator;
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
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        System.out.println(user.toString());
        if (user.getUsername() == null || user.getPassword() == null) {
            return new ResponseEntity<>("Username or Password is Empty", HttpStatus.EXPECTATION_FAILED);
        }
        User userData = userService.getUserByUserName(user.getUsername(), user.getPassword());
        if (userData == null) {
            return new ResponseEntity<>("username or password is invalid", HttpStatus.NON_AUTHORITATIVE_INFORMATION);
        }
        String str = jwtGenerator.generateToken(user).toString();
        return new ResponseEntity<>(str, HttpStatus.OK);
    }


    @GetMapping("/")
    public ResponseEntity<?> successLogin() {
        return new ResponseEntity<>("token matched success full", HttpStatus.OK);
    }

    @PostMapping("/enroll/userId={userId}")
    public ResponseEntity<?> enrollForFood(@RequestBody Availability availability, @PathVariable int userId) {

        Availability preAv = availabilityService.getExistedAval(userId);
        if (availability == null)
            return new ResponseEntity<>("days you selected include weekend choose other day in a row", HttpStatus.OK);

        LocalDate date3 = availability.getDate3();
        LocalDate date2 = availability.getDate2();
        LocalDate date1 = availability.getDate1();
        LocalDate current = LocalDate.now();
        if (
                current.equals(date1)
                && preAv.getDate3().isBefore(date1)
                && ((!date2.getDayOfWeek().toString().toLowerCase().matches("sunday|saturday"))
                && (!date3.getDayOfWeek().toString().toLowerCase().matches("sunday|saturday")))
                && validator.isConsequent(date1 , date2 , date3)
        ) {
            availabilityService.saveEmployeeStatus(availability, userId);
        }
        return new ResponseEntity<>(availability, HttpStatus.OK);
    }

    @PostMapping("/pwreset")
    public ResponseEntity<?> resetPassword(@RequestBody User user) {
        return new ResponseEntity<>(userService.resetUserPassword(user), HttpStatus.OK);
    }


}
