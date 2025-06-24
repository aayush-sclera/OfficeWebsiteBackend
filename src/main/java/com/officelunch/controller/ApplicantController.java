package com.officelunch.controller;

import com.officelunch.service.ApplicationService;
import com.officelunch.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/officeLunch/employees/api/applications")
//@CrossOrigin(origins = "https://accesssystems.com.np", allowedHeaders = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class ApplicantController {

    private final ApplicationService applicationService;
    private final CareerService careerService;

    @Autowired
    public ApplicantController(ApplicationService applicationService, CareerService careerService) {
        this.applicationService = applicationService;
        this.careerService = careerService;
    }

    @PostMapping("/apply/{careerId}")
    public ResponseEntity<String> applyForJob(
            @PathVariable Long careerId,
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("contactNumber") String contactNumber,
            @RequestParam("currentAddress") String currentAddress,
            @RequestParam("cvResume") MultipartFile cvFile) throws IOException {
        if (cvFile.isEmpty()) {
            return ResponseEntity.badRequest().body("CV file is required");
        }
        applicationService.applyForJob(careerId, name, email, contactNumber, currentAddress, cvFile);
        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Map<String, Object>>> getApplicantById(@PathVariable Long id) throws  IOException{
        List<Map<String, Object>> applicants = careerService.getAllApplicantsByCareerId(id);
        return ResponseEntity.ok(applicants);
    }

    @GetMapping("/cv/{filename:.+}")
    public ResponseEntity<Resource> getCV(@PathVariable String filename) {
        try {
            // Validate filename to prevent path traversal
            if (filename.contains("..") || filename.contains("/")) {
                return ResponseEntity.badRequest().build();
            }
            // Construct file path
            Path filePath = Paths.get("/home/ubuntu/cvFolder/").resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            // Check if resource exists and is readable
            if (resource.exists() && resource.isReadable()) {
                // Determine content type dynamically
                String contentType = determineContentType(filename);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .headers(headers)
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    private String determineContentType(String filename) {
        if (filename.toLowerCase().endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF_VALUE;
        } else if (filename.toLowerCase().endsWith(".doc") || filename.toLowerCase().endsWith(".docx")) {
            return "application/msword";
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE; // Fallback
        }
    }
}