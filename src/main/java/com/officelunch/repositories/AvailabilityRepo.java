package com.officelunch.repositories;

import com.officelunch.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface AvailabilityRepo extends JpaRepository<Availability, Integer> {


    @Query(value = "select u.email from user u  where u.id not in (select availability.user_id from availability where availability.date=:today);",nativeQuery = true)
    List<String> findAllAbsentUser(@Param("today") String today);
//    @Query(value = "select count(food_pref)as count ,food_pref from  availability where food_pref not like \"Not Selected\" group by food_pref;",nativeQuery = true)
//    @Query(value = "select count(food_pref)as count ,food_pref from  availability where date=:today group by food_pref",nativeQuery = true)

//    @Query(value = "select count(food_pref)as count ,food_pref from  availability where date=:today  group by food_pref union\n" +
//            " select count(u.email) as count, 'Not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date=:today)",nativeQuery = true)



//    @Query(value = " with cte as (select count(food_pref)as count ,food_pref from  availability where date='2023-05-28'  group by food_pref union\n" +
//            "select count(u.email) as count, 'Not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date='2023-05-28'))\n" +
//            "select json_object(\"count\",json_arrayagg(json_object(\"food_pref\",food_pref, \"count\",count)))from cte;\n" +
//            " ",nativeQuery = true)


//    @Query(value = "select count(food_pref)as count ,food_pref from  availability where date=:today  group by food_pref union select count(u.email) as count, 'Not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date=:today)",nativeQuery = true)

    @Query(value = "with default_tbl(count,food_pref) AS (values row (0,'veg'),row(0,'non-veg'),row(0,'not-responded'),row(0,'not-required')),\n" +
            "res as (select count(food_pref)as count ,food_pref from  availability where date=:today  group by food_pref union\n" +
            "select count(u.email) as count, 'not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date=:today))\n" +
            "select COALESCE (res.count,default_tbl.count) as count,default_tbl.food_pref from default_tbl left join res on res.food_pref = default_tbl.food_pref \n",nativeQuery = true)
    List<Map<Integer,String>> countAllFoodType(@Param("today") String today);
//    @Query(value = "select CASE WHEN date =:today and user_id =:userId then 'true'  ELSE 'false' END AS BOOLEAN from availability where user_id =:userId",nativeQuery = true)


    @Query(value = " select 'true' result from availability where date=:today and user_id=:userId",nativeQuery = true)
    String existByDateAndUserId(@Param("today") String today,@Param("userId") int userId);


//

//    @Query(value = "with cte as(select u.username ,food_pref from  availability a left join user u on a.user_id = u.id where a.date='2023-05-28' union \n" +
//            " select u.username, 'Not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date='2023-05-28'))\n" +
//            " select json_object(\"users\",json_arrayagg(json_object(\"username\",username,\"food_pref\",food_pref))) from cte;\n" +
//            " ",nativeQuery = true)


    @Query(value = "select u.username ,food_pref from  availability a left join user u on a.user_id = u.id where a.date=:today union select u.username, 'Not-responded' from user u  where u.id not in (select availability.user_id from availability where availability.date=:today)",nativeQuery = true)
    List<Map<Objects,String>> listOfFoodTypes(String today);


    @Query(value = "select count(food_pref) from availability where date between :date1 and :date2 and food_pref <> 'not-required'",nativeQuery = true)
    Integer countRangeTotal(@Param("date1") String date1, @Param("date2") String date2);

    @Query(value = "select count(food_pref) from availability where date =:date and food_pref <> 'not-required'",nativeQuery = true)
    Integer countDailyTotal(@Param("date") String date);


//     select count(food_pref)as count ,food_pref from  availability where date='2023-05-28'  group by food_pref union select count(u.id) as count, 'not-responded' from user u left join
//availability a on u.id=a.user_id where a.id is null;

//to count all food count for singe date
//    select count(food_pref) from officeLunch.availability where date ='2023-05-08' and food_pref <> 'non-required';

//    to count all food over a period of month
//    select count(food_pref) from officeLunch.availability where date between '2023-05-01' and '2023-05-30' and food_pref <> 'not-required';

}
