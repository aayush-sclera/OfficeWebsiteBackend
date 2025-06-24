package com.officelunch.model;


import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="career")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Career {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String type;
    private String qualificationAndExperience;
    private String description;
    private String requiredSkill;
    private String location;
    private String experience;
    private String deadline;
    private String applyLink;
    private Boolean active = Boolean.TRUE;



    //    public Career(String title, String type, List<String> qualificationAndExperience,
//                      List<String> description, List<String> requiredSkill, String location,
//                      String experience, String deadline, String applyLink) {
//        this.title = title;
//        this.type = type;
//        this.qualificationAndExperience = qualificationAndExperience;
//        this.description = description;
//        this.requiredSkill = requiredSkill;
//        this.location = location;
//        this.experience = experience;
//        this.deadline = deadline;
//        this.applyLink = applyLink;
//        this.applicants = new ArrayList<>();
//    }

    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//
//    public List<String> getQualificationAndExperience() {
//        return qualificationAndExperience;
//    }
//
//    public void setQualificationAndExperience(List<String> qualificationAndExperience) {
//        this.qualificationAndExperience = qualificationAndExperience;
//    }
//
//    public List<String> getDescription() {
//        return description;
//    }
//
//    public void setDescription(List<String> description) {
//        this.description = description;
//    }
//
//    public List<String> getRequiredSkill() {
//        return requiredSkill;
//    }
//
//    public void setRequiredSkill(List<String> requiredSkill) {
//        this.requiredSkill = requiredSkill;
//    }
//
//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }
//
//    public String getExperience() {
//        return experience;
//    }
//
//    public void setExperience(String experience) {
//        this.experience = experience;
//    }
//
//    public String getDeadline() {
//        return deadline;
//    }
//
//    public void setDeadline(String deadline) {
//        this.deadline = deadline;
//    }
//
//    public String getApplyLink() {
//        return applyLink;
//    }
//
//    public void setApplyLink(String applyLink) {
//        this.applyLink = applyLink;
//    }
//    public List<Applicant> getApplicants() {
//        return applicants;
//    }
//
//    public void setApplicants(List<Applicant> applicants) {
//        this.applicants = applicants;
//    }
//
//    // Helper method to add an applicant
//    public void addApplicant(Applicant applicant) {
//        applicants.add(applicant);
////        applicant.setCareer(this);
//    }
    }
