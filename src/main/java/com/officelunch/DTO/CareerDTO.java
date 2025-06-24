package com.officelunch.DTO;

import com.officelunch.model.Applicant;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CareerDTO {
    private Long id;
    private String title;
    private String type;
    private List<String> qualificationAndExperience;
    private List<String> description;
    private List<String> requiredSkill;
    private String location;
    private String experience;
    private String deadline;
    private String applyLink;
    private List<Applicant> applicants; // Optional: convert applicants to DTO too
}
