package com.officelunch.controller;

import com.officelunch.repositories.AvailabilityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

@RestController
public class test {

    @Autowired
    AvailabilityRepo availabilityRepo;

    @GetMapping("/get")
   public String ret(){
       return "hello";
   }


}
