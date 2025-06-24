package com.officelunch.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ApplicationService {
    void applyForJob(Long careerId, String name, String email, String contactNumber, String currentAddress, MultipartFile cvFile) throws IOException;
}
