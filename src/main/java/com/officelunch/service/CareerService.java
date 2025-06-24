package com.officelunch.service;

import com.officelunch.DTO.CareerDTO;
import com.officelunch.model.Applicant;
import com.officelunch.model.Career;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CareerService {
    List<CareerDTO> getAllJobOpenings();

    List<Career> getAllCareers();

    CareerDTO getJobOpeningById(Long id);

    Career createJobOpening(Career jobOpening);

    Career updateJobOpening(Long id, Career jobOpeningDetails);

    void deleteJobOpening(Long id);

    List<Map<String,Object>> getAllApplicantsByCareerId(Long id);

    @Transactional
    void updateActiveStatus(Long id, boolean active);

    List<Career> getActiveCareer();
}
