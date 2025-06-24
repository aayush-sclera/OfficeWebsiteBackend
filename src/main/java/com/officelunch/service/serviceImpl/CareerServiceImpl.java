package com.officelunch.service.serviceImpl;

import com.officelunch.DTO.CareerDTO;
import com.officelunch.Mapper.CareerMapper;
import com.officelunch.model.Career;
import com.officelunch.repositories.ApplicantRepo;
import com.officelunch.repositories.CareerRepo;
import com.officelunch.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CareerServiceImpl implements CareerService {

    @Autowired
    private CareerRepo careerRepo;
    @Autowired
    private CareerMapper careerMapper;
    @Autowired
    private ApplicantRepo applicantRepo;

    @Override
    public List<CareerDTO> getAllJobOpenings() {
        return careerRepo.findAll().stream()
                .map(careerMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<Career> getAllCareers() {
        return careerRepo.findAll();
    }

    @Override
    public CareerDTO getJobOpeningById(Long id) {
        return careerMapper.toDTO(careerRepo.findById(id).get());
    }

    @Override
    public Career createJobOpening(Career jobOpening) {
        return careerRepo.save(jobOpening);
    }

    @Override
    public Career updateJobOpening(Long id, Career jobOpeningDetails) {
        Optional<Career> jobOptional = careerRepo.findById(id);
        if (jobOptional.isPresent()) {
            Career job = jobOptional.get();
            job.setTitle(jobOpeningDetails.getTitle());
            job.setType(jobOpeningDetails.getType());
            job.setQualificationAndExperience(jobOpeningDetails.getQualificationAndExperience());
            job.setDescription(jobOpeningDetails.getDescription());
            job.setRequiredSkill(jobOpeningDetails.getRequiredSkill());
            job.setLocation(jobOpeningDetails.getLocation());
            job.setExperience(jobOpeningDetails.getExperience());
            job.setDeadline(jobOpeningDetails.getDeadline());
            job.setApplyLink(jobOpeningDetails.getApplyLink());
            return careerRepo.save(job);
        }
        return null;
    }

    @Override
    public void deleteJobOpening(Long id) {
        careerRepo.deleteById(id);
    }

    @Override
    public List<Map<String,Object>> getAllApplicantsByCareerId(Long id) {
        return applicantRepo.findApplicantsByCareerId(id);
    }

    @Transactional
    @Override
    public void updateActiveStatus(Long id, boolean active) {
        careerRepo.updateActiveStatus(id, active);
    }

    @Override
    public List<Career> getActiveCareer(){
       return careerRepo.getActiveCareer();
    }
}
