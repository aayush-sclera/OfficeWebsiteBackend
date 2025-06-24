package com.officelunch.controller;

import com.azure.core.annotation.Put;
import com.officelunch.DTO.CareerDTO;
import com.officelunch.model.Career;
import com.officelunch.service.CareerService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/officeLunch/employees/api/careers")
//@CrossOrigin(origins = "https://accesssystems.com.np", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

//    @GetMapping("/get")
//    public ResponseEntity<List<CareerDTO>> getAllJobOpenings() {
//        try {
//            System.out.println("checking");
//            List<CareerDTO> jobOpenings = careerService.getAllJobOpenings();
//            System.out.println("hello");
//            return new ResponseEntity<>(jobOpenings, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    @GetMapping("/get2")
    public ResponseEntity<List<Career>> getAllCareers() {
        List<Career> careers = careerService.getAllCareers();
        return new ResponseEntity<>(careers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDTO> getJobOpeningById(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            CareerDTO jobOpening = careerService.getJobOpeningById(id);
//            return jobOpening.map(career -> new ResponseEntity<>(career, HttpStatus.OK))
//                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
            return new ResponseEntity<>(jobOpening,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Career> createJobOpening( @RequestBody Career jobOpening) {
        try {
            Career createdJob = careerService.createJobOpening(jobOpening);
            return new ResponseEntity<>(createdJob, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Career> updateJobOpening(@PathVariable Long id,  @RequestBody Career jobOpeningDetails) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            Career updatedJob = careerService.updateJobOpening(id, jobOpeningDetails);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOpening(@PathVariable Long id) {
        if (id == null || id <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            careerService.deleteJobOpening(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}/active")
    public String updateActive(@PathVariable Long id, @RequestParam boolean active) {
        careerService.updateActiveStatus(id, active);
        return "Active status updated for career ID: " + id;
    }
    @GetMapping("/get")
    public List<Career> getActiveCareer(){
        return careerService.getActiveCareer();
    }
    @GetMapping("/test")
    public String getTest(){
        return "test";
    }
}