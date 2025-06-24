package com.officelunch.service.serviceImpl;

import com.officelunch.model.Applicant;
import com.officelunch.model.Career;
import com.officelunch.repositories.ApplicantRepo;
import com.officelunch.repositories.CareerRepo;
import com.officelunch.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private CareerRepo careerRepo;

    @Autowired
    private ApplicantRepo applicantRepo;
//    private static final String LOCAL_UPLOAD_DIR = "C:\\Users\\youba\\Downloads\\";
    private static final String SERVER_UPLOAD_DIR = "/home/ubuntu/cvFolder";
    @Override
    public void applyForJob(Long careerId, String name, String email, String contactNumber, String currentAddress, MultipartFile cvFile) throws IOException {


        if (cvFile.isEmpty()) {
            throw new IOException("CV file is empty");
        }
        String contentType = cvFile.getContentType();
        if (!isValidFileType(contentType)) {
            throw new IOException("Invalid file type. Only PDF and DOCX are allowed.");
        }
        // Generate unique filename to avoid conflicts
        String originalFilename = cvFile.getOriginalFilename();
        String fileExtension = getFileExtension(originalFilename);
        String uniqueFilename = UUID.randomUUID().toString() + fileExtension;
        saveFileLocally(cvFile, uniqueFilename);
        // Create and populate the applicant
        Applicant applicant = new Applicant();
        applicant.setName(name);
        applicant.setEmail(email);
        applicant.setContactNumber(contactNumber);
        applicant.setCurrentAddress(currentAddress);
        applicant.setCvResume(uniqueFilename);
         applicant.setCareerId(careerId);

        // Save the applicant
        applicantRepo.save(applicant);
    }
    private boolean isValidFileType(String contentType) {
        return contentType != null && (
                contentType.equals("application/pdf") ||
                        contentType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document"));
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".pdf"; // Default extension if none provided
        }
        return filename.substring(filename.lastIndexOf("."));
    }

    private void saveFileLocally(MultipartFile file, String filename) throws IOException {
        // Create local directory if it doesn't exist
        File directory = new File(SERVER_UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Save file to local directory
        Path filePath = Paths.get(SERVER_UPLOAD_DIR + "/"+ filename);
        Files.write(filePath,file.getBytes() );
    }
}
