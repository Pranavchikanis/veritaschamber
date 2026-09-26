package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.request.FaqCreateRequest;
import com.veritaschambers.dto.request.FaqUpdateRequest;
import com.veritaschambers.dto.response.FaqResponse;
import com.veritaschambers.service.FaqService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/faqs")
public class AdminFaqController {

    private final FaqService faqService;

    public AdminFaqController(FaqService faqService) {
        this.faqService = faqService;
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<FaqResponse>> getAllFaqs(org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(faqService.getAllFaqs(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqResponse> getFaqById(@PathVariable Long id) {
        return ResponseEntity.ok(faqService.getFaqById(id));
    }

    @PostMapping
    public ResponseEntity<FaqResponse> createFaq(
            @Valid @RequestBody FaqCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(faqService.createFaq(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaqResponse> updateFaq(
            @PathVariable Long id,
            @Valid @RequestBody FaqUpdateRequest request) {
        return ResponseEntity.ok(faqService.updateFaq(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaq(@PathVariable Long id) {
        faqService.deleteFaq(id);
        return ResponseEntity.noContent().build();
    }
}
