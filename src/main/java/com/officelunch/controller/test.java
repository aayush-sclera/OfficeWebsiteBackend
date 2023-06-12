package com.officelunch.controller;

import com.officelunch.repositories.AvailabilityRepo;
import com.officelunch.service.AvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class test {

    @Autowired
    AvailabilityRepo availabilityRepo;
    @Autowired
    AvailabilityService availabilityService;

    @GetMapping("/get")
   public String ret(){
       return "Test Controller";
   }


    @PostMapping("/range")
    public ResponseEntity<?> countOfMonth(@RequestBody Map<String,Object> payload){
        String date1 = payload.get("date1").toString();
        String date2 = payload.get("date2").toString();
        return ResponseEntity.ok().body(availabilityService.countRangeTotal(date1,date2));
    }
    @PostMapping("/single")
    public ResponseEntity<?> countOfDay(@RequestBody Map<String ,Object> payload){
        String date = (String) payload.get("date");

//        System.out.println(date);
        return ResponseEntity.ok().body(availabilityService.countDailyTotal(date.toString()));
    }


}
