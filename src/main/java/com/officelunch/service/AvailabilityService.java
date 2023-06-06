package com.officelunch.service;

import com.officelunch.model.Availability;

import javax.persistence.criteria.CriteriaBuilder;

public interface AvailabilityService {

    Availability getExistedAval(int userId);
    Availability saveEmployeeStatus(Availability availability);

    Integer countRangeTotal(String date1, String date2);

    Integer countDailyTotal(String date);
}
