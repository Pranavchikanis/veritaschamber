package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.request.LawyerProfileUpdateRequest;
import com.veritaschambers.dto.response.LawyerProfileResponse;
import com.veritaschambers.service.LawyerProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/lawyer-profile")
public class AdminLawyerProfileController {

    private final LawyerProfileService lawyerProfileService;

    public AdminLawyerProfileController(LawyerProfileService lawyerProfileService) {
        this.lawyerProfileService = lawyerProfileService;
    }

    @GetMapping
    public ResponseEntity<LawyerProfileResponse> getLawyerProfile() {
        return ResponseEntity.ok(lawyerProfileService.getLawyerProfile());
    }

    @PutMapping
    public ResponseEntity<LawyerProfileResponse> updateLawyerProfile(
            @Valid @RequestBody LawyerProfileUpdateRequest request) {
        return ResponseEntity.ok(lawyerProfileService.updateLawyerProfile(request));
    }
}
