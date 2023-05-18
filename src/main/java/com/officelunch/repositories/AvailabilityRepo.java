package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {


    @Query(value = "select u.email  from user u join availability a on a.user_id=u.id where a.attendance='Absent'",nativeQuery = true)
    List<String> findAllAbsentUser();
}
