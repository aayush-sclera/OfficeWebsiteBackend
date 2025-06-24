package com.officelunch.model;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Table;

@Entity
@Table(name = "applicant")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class Applicant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String contactNumber;

    private String currentAddress;

    private String cvResume;

    private Long careerId;


//    public Applicant(String name, String email, String contactNumber, String currentAddress, byte[] cvResume, Career career) {
//        this.name = name;
//        this.email = email;
//        this.contactNumber = contactNumber;
//        this.currentAddress = currentAddress;
//        this.cvResume = cvResume;
//    }
//
//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(String contactNumber) {
//        this.contactNumber = contactNumber;
//    }
//
//    public String getCurrentAddress() {
//        return currentAddress;
//    }
//
//    public void setCurrentAddress(String currentAddress) {
//        this.currentAddress = currentAddress;
//    }
//
//    public byte[] getCvResume() {
//        return cvResume;
//    }
//
//    public void setCvResume(byte[] cvResume) {
//        this.cvResume = cvResume;
//    }


}