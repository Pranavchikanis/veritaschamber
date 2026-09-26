package com.veritaschambers.controller.publicapi;

import com.veritaschambers.dto.response.TestimonialResponse;
import com.veritaschambers.service.TestimonialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public/testimonials")
public class PublicTestimonialController {

    private final TestimonialService testimonialService;

    public PublicTestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @GetMapping
    public ResponseEntity<List<TestimonialResponse>> getPublishedTestimonials() {
        return ResponseEntity.ok(testimonialService.getPublishedTestimonials());
    }
}
