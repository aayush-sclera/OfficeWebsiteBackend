package com.officelunch.service.serviceImpl;

import com.officelunch.model.Availability;
import com.officelunch.repositories.AvailabilityRepo;
import com.officelunch.service.AvailabilityService;
import com.officelunch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    public Availability saveEmployeeStatus(Availability availability) {
      return availabilityRepo.save(availability);
    }

    @Override
    public List<Map<Objects, String>> countRangeTotal(String date1, String date2) {
        return availabilityRepo.countRangeTotal(date1,date2);
    }

    @Override
    public List<Map<Objects, String>> countDailyTotal(String date) {
        return availabilityRepo.countDailyTotal(date);
    }
}
