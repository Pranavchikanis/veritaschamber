package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.request.PracticeAreaCreateRequest;
import com.veritaschambers.dto.request.PracticeAreaUpdateRequest;
import com.veritaschambers.dto.response.PracticeAreaResponse;
import com.veritaschambers.service.PracticeAreaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/practice-areas")
public class AdminPracticeAreaController {

    private final PracticeAreaService practiceAreaService;

    public AdminPracticeAreaController(PracticeAreaService practiceAreaService) {
        this.practiceAreaService = practiceAreaService;
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<PracticeAreaResponse>> getAllPracticeAreas(org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(practiceAreaService.getAllPracticeAreas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PracticeAreaResponse> getPracticeAreaById(@PathVariable Long id) {
        return ResponseEntity.ok(practiceAreaService.getPracticeAreaById(id));
    }

    @PostMapping
    public ResponseEntity<PracticeAreaResponse> createPracticeArea(
            @Valid @RequestBody PracticeAreaCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(practiceAreaService.createPracticeArea(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PracticeAreaResponse> updatePracticeArea(
            @PathVariable Long id,
            @Valid @RequestBody PracticeAreaUpdateRequest request) {
        return ResponseEntity.ok(practiceAreaService.updatePracticeArea(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePracticeArea(@PathVariable Long id) {
        practiceAreaService.deletePracticeArea(id);
        return ResponseEntity.noContent().build();
    }
}
