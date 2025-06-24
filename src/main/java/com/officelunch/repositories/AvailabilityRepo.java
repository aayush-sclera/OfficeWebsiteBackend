package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {


    @Query(value = "select u.email from user u  where u.id not in (select availability.user_id from availability where availability.date=:today);",nativeQuery = true)
    List<String> findAllAbsentUser(@Param("today") String today);


    @Query(value = "with default_tbl(count,food_pref) AS (values row (0,'veg'),row(0,'non-veg'),row(0,'salad'),row(0,'not-responded'),row(0,'not-required')),\n" +
            "res as (select count(food_pref)as count ,food_pref from  availability where date=:today  group by food_pref union\n" +
            "select count(u.email) as count, 'not-responded' from user u  where u.is_employee = FALSE AND u.id not in (select availability.user_id from availability where availability.date=:today))\n" +
            "select COALESCE (res.count,default_tbl.count) as count,default_tbl.food_pref from default_tbl left join res on res.food_pref = default_tbl.food_pref \n",nativeQuery = true)
    List<Map<Integer,String>> countAllFoodType(@Param("today") String today);


    @Query(value = " select 'true' result from availability where date=:today and user_id=:userId",nativeQuery = true)
    String existByDateAndUserId(@Param("today") String today,@Param("userId") int userId);


    @Query(value = "select u.username ,food_pref from  availability a left join user u on a.user_id = u.id where a.date=:today union select u.username, 'Not-responded' from user u  where u.is_employee = FALSE AND u.id not in (select availability.user_id from availability where availability.date=:today)",nativeQuery = true)
    List<Map<Objects,String>> listOfFoodTypes(String today);


    @Query(value ="with default_tbl (count,food_pref) as (values row(0,'veg'),row(0,'non-veg'),row(0,'salad')),\n" +
            "res as (select count(food_pref) as count, food_pref from availability where date BETWEEN  :date1 and :date2 and food_pref <> 'not-required' GROUP by food_pref )\n" +
            "select COALESCE (res.count,default_tbl.count) as count, default_tbl.food_pref from default_tbl left join res using(food_pref) union\n" +
            "select sum(count) , 'total' from res;",nativeQuery = true)
    List<Map<Objects,String>> countRangeTotal(@Param("date1") String date1, @Param("date2") String date2);

    @Query(value = "with default_tbl (count,food_pref) as (values row(0,'veg'),row(0,'non-veg'),row(0,'salad')),\n" +
            "res as (select count(food_pref) as count, food_pref from availability where date=:date and food_pref <> 'not-required' GROUP by food_pref )\n" +
            "select COALESCE (res.count,default_tbl.count) as count, default_tbl.food_pref from default_tbl left join res using(food_pref) union\n" +
            "select sum(count) , 'total' from res;",nativeQuery = true)
    List<Map<Objects,String>> countDailyTotal(@Param("date") String date);

}
