package com.officelunch.service;

import com.officelunch.model.Availability;

public interface AvailabilityService {

    Availability getExistedAval(int userId);
    Availability saveEmployeeStatus(Availability availability);

}
