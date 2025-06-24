package com.officelunch.repositories;

import com.officelunch.model.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CareerRepo  extends JpaRepository<Career, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "Update career c set c.active= :active where c.id= :id")
    void updateActiveStatus(Long id, Boolean active);

    @Query(nativeQuery = true,
            value = " select * from career where active= true")
    List<Career> getActiveCareer ();


}
