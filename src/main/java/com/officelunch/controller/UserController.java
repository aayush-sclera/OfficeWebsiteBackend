package com.officelunch.controller;

import com.officelunch.helper.ConsequentDaysValidator;
import com.officelunch.model.Availability;
import com.officelunch.model.TokenResponse;
import com.officelunch.model.User;
import com.officelunch.repositories.UserRepositories;
//import com.officelunch.security.JwtTokenUtil;
import com.officelunch.security.JavaTokenUtil;
import com.officelunch.security.UserSpringDetails;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.UserService;
import com.officelunch.service.UserServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/officeLunch/employees")
@CrossOrigin(origins = "http://192.168.1.66:3000", allowedHeaders = "*")
public class UserController {

    @Autowired
    JavaTokenUtil jwt;
    @Autowired
    UserRepositories userRepositories;
    @Autowired
    private UserService userService;
    @Autowired
    private UserServiceTwo userServiceTwo;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private ConsequentDaysValidator validator;

    @Autowired
    PasswordEncoder passwordEncoder;
//    @PostMapping("/register")
//    public TokenResponse postUser(@RequestBody User user) {
//        System.out.println(user.getUsername());
//        if (userService.saveUser(user)!=null){
//            if (userServiceTwo.register(user)!=null){
//                return new ResponseEntity<>(user, HttpStatus.CREATED);
//
//            }else {
//                return ResponseEntity.badRequest().body("Duplicate Entry");
//            }
//        HttpStatus created = HttpStatus.CREATED;
//        return new ResponseEntity<>(HttpStatus.CREATED);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        return ResponseEntity.ok().body(userService.saveUser(user));
    }
//
//        }
//    @PostMapping("/login")
//    public ResponseEntity<?> loginUser(@RequestBody User user, HttpSession session) {
//        if (user.getUsername() == null || user.getPassword() == null) {
//            return new ResponseEntity<>("Username or Password is Empty", HttpStatus.EXPECTATION_FAILED);
//        }
//        if (!userRepositories.existsByUsername(user.getUsername())){
//            return ResponseEntity.badRequest().body("Username is not registered. Register first To Login");
//        }
//        User userData = userService.getUserByUserName(user.getUsername(), user.getPassword());
//        if (userData == null) {
//            return new ResponseEntity<>("username or password is invalid", HttpStatus.BAD_GATEWAY);
//        } else {
//           session.setAttribute("username", user.getUsername());
//          System.out.println(session.getAttributeNames()+ "++++");
//            return new ResponseEntity<>(user.getUsername(), HttpStatus.OK);
//
////            return  ResponseEntity.ok().headers();
//        }
//    }

    @PostMapping("/login")
    public String authenticateuserandgenerateToken(@RequestBody User user) {

        try {
            System.out.println("-----" + user.getUsername() + user.getPassword());
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            if (authentication.isAuthenticated()) {

                TokenResponse tok = new TokenResponse();
//                tok.setToken(jwt.generateToken(user.getUsername()));
                String tokensss = jwt.generateToken(user.getUsername());
                System.out.println(tok);
                return tokensss;
            } else {
                throw new UsernameNotFoundException(user.getUsername());
            }
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return null;
    }

    //    @PostMapping("/enroll")
//    public ResponseEntity<?> enrollForFood(@RequestBody Availability availability) {
////        HttpSession session = request.getSession();
////        String name = (String) session.getAttribute("username");
//        String name = availability.getUsername();
//        if (name!=null && name.equals(availability.getUsername())) {
//            User user = userRepositories.findByUsername(name);
//            availability.setUser(user);
//            availability.setAttendance("Present");
//            return ResponseEntity.ok().body(availabilityService.saveEmployeeStatus(availability, user.getId()));
//        } else {
//            if(!userRepositories.existsByUsername(availability.getUsername())){
//                return ResponseEntity.badRequest().body("user is not registered. Register First to enroll for food");
//            }else
//            {
//                return ResponseEntity.badRequest().body("provided username is not logged in. Log in First To order food");
//            }
//        }
//    }
//    @PostMapping("/enroll")
//    public ResponseEntity<?> enrollForFood(@RequestBody Availability availability) {
////        HttpSession session = request.getSession();
////        String name = (String) session.getAttribute("username");
////            jwt.getUsernameFromToken()
//             user = userRepositories.findByUsername(availability.getUsername());
//            availability.setUser(user);
//            availability.setAttendance("Present");
//            return ResponseEntity.ok().body(availabilityService.saveEmployeeStatus(availability, user.getId()));
////        } else {
////            if(!userRepositories.existsByUsername(availability.getUsername())){
////                return ResponseEntity.badRequest().body("user is not registered. Register First to enroll for food");
////            }else
////            {
////                return ResponseEntity.badRequest().body("provided username is not logged in. Log in First To order food");
////            }
////        }
//    }
    @PostMapping("/enroll")
    public ResponseEntity<?> postsss(@RequestBody Availability availability) {

        UserSpringDetails user = (UserSpringDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername();
        User usr = userRepositories.findByUsername(name);
        availability.setFoodPref(availability.getFoodPref());
        availability.setUser(usr);
        availability.setAttendance("Present");

        return new ResponseEntity<>(availabilityService.saveEmployeeStatus(availability, usr.getId()), HttpStatus.OK);

    }


    @PostMapping("/pwReset")
    public ResponseEntity<?> resetPassword(@RequestBody User user, HttpServletRequest request) {
        return new ResponseEntity<>(userService.resetUserPassword(user), HttpStatus.OK);

    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "user logged out successfully";
    }


}
