package com.officelunch.controller;

import com.officelunch.helper.ConsequentDaysValidator;
import com.officelunch.model.Availability;
import com.officelunch.model.TokenResponse;
import com.officelunch.model.User;
import com.officelunch.repositories.AvailabilityRepo;
import com.officelunch.repositories.UserRepositories;
//import com.officelunch.security.JwtTokenUtil;
import com.officelunch.security.JavaTokenUtil;
import com.officelunch.security.UserSpringDetails;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.UserService;
import com.officelunch.service.UserServiceTwo;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/officeLunch/employees")
//@CrossOrigin(origins = "http://192.168.1.66:3000", allowedHeaders = "*")
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
    AvailabilityRepo availabilityRepo;
    @Autowired
    private AvailabilityService availabilityService;
    @Autowired
    private ConsequentDaysValidator validator;

    @Autowired
    BCryptPasswordEncoder encoder;

//    @Autowired
//    PasswordEncoder passwordEncoder;

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
        if (userRepositories.existsByUsername(user.getUsername().toLowerCase())) {
            return ResponseEntity.badRequest().body("Duplicate entry of Username");
        }
        if (userRepositories.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Duplicate entry of Email");
        }
        if (Pattern.compile("@accessonline.io|@gmail.com").matcher(user.getEmail()).find()) {
            if (user.getPassword().equals(user.getConfirmPass())) {
                userService.saveUser(user);
                System.out.println("helllllllllll");
            } else {
                return ResponseEntity.badRequest().body("Password Do not match");
            }
            return ResponseEntity.ok().body("Register success");
        } else {
            return ResponseEntity.badRequest().body("Error: Email must contain  domain name '@accessonline.io' and '@gmail.com' ");
        }
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
    public ResponseEntity<?> authenticateuserandgenerateToken(@RequestBody User user, Principal principal) {
        User usr = userRepositories.findByUsername(principal.getName());
        if (encoder.matches(user.getPassword(), usr.getPassword())) {
            try {
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername().toLowerCase(), user.getPassword()));
                if (authentication.isAuthenticated()) {

                    TokenResponse tok = new TokenResponse();
//                tok.setToken(jwt.generateToken(user.getUsername()));
                    String tokensss = jwt.generateToken(user.getUsername().toLowerCase());
                    return ResponseEntity.ok().body(tokensss);
                } else {
                    throw new UsernameNotFoundException(user.getUsername());
                }
            } catch (AuthenticationException e) {
                e.printStackTrace();
            }

        }
        return ResponseEntity.badRequest().body("Password Do not Match");
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


    @GetMapping("/getall")
    public ResponseEntity<?> getAllCountOfVegAndNonVeg() {
        return ResponseEntity.ok().body(availabilityRepo.countAllVegandNonveg());
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> postsss(@RequestBody Availability availability) {


        UserSpringDetails user = (UserSpringDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String name = user.getUsername().toLowerCase();
        User usr = userRepositories.findByUsername(name);
        LocalDate prevDate = availabilityRepo.findById(usr.getId()).get().getDate();
//        if (prevDate==null) availability.setDate(LocalDate.now());
        LocalDate today = LocalDate.now();
        if (!today.isEqual(prevDate)) {
            availability.setFoodPref(availability.getFoodPref());
            availability.setUser(usr);
            availability.setDate(today);
            availability.setAttendance("Present");
            return new ResponseEntity<>(availabilityService.saveEmployeeStatus(availability, usr.getId()), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("You have already inserted for today");
        }


    }


    @PostMapping("/pwReset")
    public ResponseEntity<?> resetPassword(@RequestBody User user, HttpServletRequest request) {
        if (user.getPassword().equals(user.getConfirmPass())) {

            return new ResponseEntity<>(userService.resetUserPassword(user), HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().body("Password donot match ");
        }

    }

    @PostMapping("/logout")
    public String logout() {
        return "user logged out successfully";
    }


}
