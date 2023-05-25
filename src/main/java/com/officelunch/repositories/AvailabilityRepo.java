package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {


    @Query(value = "select u.email  from user u join availability a on a.user_id=u.id where a.attendance='Absent'",nativeQuery = true)
    List<String> findAllAbsentUser();


    @Query(value = "select count(food_pref)as count ,food_pref from  availability where food_pref not like \"Not Selected\" group by food_pref;",nativeQuery = true)
    List<Map<Integer,String >> countAllVegandNonveg();
}
