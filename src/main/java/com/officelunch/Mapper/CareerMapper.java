package com.officelunch.Mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.officelunch.DTO.CareerDTO;
import com.officelunch.model.Career;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CareerMapper {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public CareerDTO toDTO(Career career) {
        CareerDTO dto = new CareerDTO();
        dto.setId(career.getId());
        dto.setTitle(career.getTitle());
        dto.setType(career.getType());
        dto.setLocation(career.getLocation());
        dto.setExperience(career.getExperience());
        dto.setDeadline(career.getDeadline());
        dto.setApplyLink(career.getApplyLink());
//        dto.setApplicants(career.getApplicants()); // Optional: Map to ApplicantDTO

        try {
            dto.setQualificationAndExperience(objectMapper.readValue(
                    career.getQualificationAndExperience(), new TypeReference<List<String>>() {}));
            dto.setDescription(objectMapper.readValue(
                    career.getDescription(), new TypeReference<List<String>>() {}));
            dto.setRequiredSkill(objectMapper.readValue(
                    career.getRequiredSkill(), new TypeReference<List<String>>() {}));
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse JSON fields", e);
        }

        return dto;
    }
}
