package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.service.ConsultationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public/consultations")
public class PublicConsultationController {

    private final ConsultationService consultationService;

    public PublicConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @PostMapping
    public ResponseEntity<ConsultationResponse> createConsultation(
            @Valid @RequestBody ConsultationCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(consultationService.createConsultation(request));
    }
}
