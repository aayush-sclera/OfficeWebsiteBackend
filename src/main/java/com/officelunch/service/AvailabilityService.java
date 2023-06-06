package com.officelunch.service;

import com.officelunch.model.Availability;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface AvailabilityService {

    Availability getExistedAval(int userId);
    Availability saveEmployeeStatus(Availability availability);

    List<Map<Objects,String>> countRangeTotal(String date1, String date2);

    List<Map<Objects,String>> countDailyTotal(String date);
}
