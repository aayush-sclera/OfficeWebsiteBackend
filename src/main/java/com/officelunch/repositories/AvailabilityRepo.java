package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {


    @Query(value = "select u.email  from user u join availability a on a.user_id=u.id where a.attendance='Absent'",nativeQuery = true)
    List<String> findAllAbsentUser();


//    @Query(value = "select count(food_pref)as count ,food_pref from  availability where food_pref not like \"Not Selected\" group by food_pref;",nativeQuery = true)
    @Query(value = "select count(food_pref)as count ,food_pref from  availability where date=:today group by food_pref",nativeQuery = true)
    List<Map<Integer,String >> countAllFoodType(@Param("today") String today);
//    @Query(value = "select CASE WHEN date =:today and user_id =:userId then 'true'  ELSE 'false' END AS BOOLEAN from availability where user_id =:userId",nativeQuery = true)
    @Query(value = " select 'true' result from availability where date=:today and user_id=:userId",nativeQuery = true)
    String existByDateAndUserId(@Param("today") String today,@Param("userId") int userId);
}
