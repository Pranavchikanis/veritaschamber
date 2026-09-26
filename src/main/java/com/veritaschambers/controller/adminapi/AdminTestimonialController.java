package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.request.TestimonialCreateRequest;
import com.veritaschambers.dto.request.TestimonialUpdateRequest;
import com.veritaschambers.dto.response.TestimonialResponse;
import com.veritaschambers.service.TestimonialService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/testimonials")
public class AdminTestimonialController {

    private final TestimonialService testimonialService;

    public AdminTestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @GetMapping
    public ResponseEntity<Page<TestimonialResponse>> getAllTestimonials(Pageable pageable) {
        return ResponseEntity.ok(testimonialService.getAllTestimonials(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestimonialResponse> getTestimonialById(@PathVariable Long id) {
        return ResponseEntity.ok(testimonialService.getTestimonialById(id));
    }

    @PostMapping
    public ResponseEntity<TestimonialResponse> createTestimonial(
            @Valid @RequestBody TestimonialCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(testimonialService.createTestimonial(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponse> updateTestimonial(
            @PathVariable Long id,
            @Valid @RequestBody TestimonialUpdateRequest request) {
        return ResponseEntity.ok(testimonialService.updateTestimonial(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestimonial(@PathVariable Long id) {
        testimonialService.deleteTestimonial(id);
        return ResponseEntity.noContent().build();
    }
}
