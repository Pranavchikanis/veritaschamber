package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.response.PracticeAreaResponse;
import com.veritaschambers.service.PracticeAreaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public/practice-areas")
public class PublicPracticeAreaController {

    private final PracticeAreaService practiceAreaService;

    public PublicPracticeAreaController(PracticeAreaService practiceAreaService) {
        this.practiceAreaService = practiceAreaService;
    }

    @GetMapping
    public ResponseEntity<List<PracticeAreaResponse>> getActivePracticeAreas() {
        return ResponseEntity.ok(practiceAreaService.getActivePracticeAreas());
    }

    @GetMapping("/{slug}")
    public ResponseEntity<PracticeAreaResponse> getPracticeAreaBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(practiceAreaService.getPracticeAreaBySlug(slug));
    }
}
