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
        int counter = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String[] available = new String[3];
        available[0] = availability.getDate1().toString();
        available[1] = availability.getDate2().toString();
        available[2] = availability.getDate3().toString();

        for (String av : available) {
            String dayName = String.valueOf(LocalDate.parse(av.toString(), formatter).getDayOfWeek()).toLowerCase();
            if (dayName.equals("saturday") || dayName.equals("sunday")) {
                System.out.println(av + "is a weekend");
            } else {
                counter++;
            }
        }

        if (counter == 3) {
            System.out.println(availabilityRepo.findById(userId).isPresent());
            if (availabilityRepo.findById(userId).isPresent()) {
                availability.setId(userId);
                availability.setUser(userService.getUserByUserId(userId));
                availability.setIsPresent(true);
            }else{
                availability.setUser(userService.getUserByUserId(userId));
            }
            return availabilityRepo.save(availability);
        } else {
            return null;
        }
    }
}
