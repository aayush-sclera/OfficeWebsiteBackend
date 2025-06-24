package com.officelunch.repositories;

import com.officelunch.model.Applicant;
import org.hibernate.query.NativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant, Long> {
    List<Applicant> findByCareerId(Long careerId);

    @Query(nativeQuery = true,
            value = " select * from applicant where career_id = :careerId")
    List<Map<String, Object>> findApplicantsByCareerId (@Param("careerId" ) Long careerId);

}
