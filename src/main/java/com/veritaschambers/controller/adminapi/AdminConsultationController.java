package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.entity.enums.ConsultationStatus;
import com.veritaschambers.service.ConsultationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/consultations")
public class AdminConsultationController {

    private final ConsultationService consultationService;

    public AdminConsultationController(ConsultationService consultationService) {
        this.consultationService = consultationService;
    }

    @GetMapping
    public ResponseEntity<Page<ConsultationResponse>> getAllConsultations(Pageable pageable) {
        return ResponseEntity.ok(consultationService.getAllConsultations(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationResponse> getConsultationById(@PathVariable Long id) {
        return ResponseEntity.ok(consultationService.getConsultationById(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ConsultationResponse> updateConsultationStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> statusUpdate) {
        ConsultationStatus status = ConsultationStatus.valueOf(statusUpdate.get("status").toUpperCase());
        return ResponseEntity.ok(consultationService.updateConsultationStatus(id, status));
    }
}
