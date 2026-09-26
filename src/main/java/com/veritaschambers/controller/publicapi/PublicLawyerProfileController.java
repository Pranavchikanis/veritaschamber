package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.response.LawyerProfileResponse;
import com.veritaschambers.service.LawyerProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/lawyer-profile")
public class PublicLawyerProfileController {

    private final LawyerProfileService lawyerProfileService;

    public PublicLawyerProfileController(LawyerProfileService lawyerProfileService) {
        this.lawyerProfileService = lawyerProfileService;
    }

    @GetMapping
    public ResponseEntity<LawyerProfileResponse> getLawyerProfile() {
        return ResponseEntity.ok(lawyerProfileService.getLawyerProfile());
    }
}
