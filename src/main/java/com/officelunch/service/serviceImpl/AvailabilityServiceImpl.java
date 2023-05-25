package com.officelunch.service.serviceImpl;

import com.officelunch.model.Availability;
import com.officelunch.repositories.AvailabilityRepo;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    @Autowired
    AvailabilityRepo availabilityRepo;
    @Autowired
    UserService userService;

    @Override
    public Availability getExistedAval(int userId) {
        return availabilityRepo.findById(userId).get();
    }

    @Override
    public Availability saveEmployeeStatus(Availability availability, int userId) {
      availability.setId(userId);
      return availabilityRepo.save(availability);
    }
}
