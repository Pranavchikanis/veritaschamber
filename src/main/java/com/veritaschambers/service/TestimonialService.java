package com.veritaschambers.service;

import com.veritaschambers.dto.request.TestimonialCreateRequest;
import com.veritaschambers.dto.request.TestimonialUpdateRequest;
import com.veritaschambers.dto.response.TestimonialResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestimonialService {
    TestimonialResponse createTestimonial(TestimonialCreateRequest request);
    TestimonialResponse updateTestimonial(Long id, TestimonialUpdateRequest request);
    Page<TestimonialResponse> getAllTestimonials(Pageable pageable);
    java.util.List<TestimonialResponse> getPublishedTestimonials();
    TestimonialResponse getTestimonialById(Long id);
    void deleteTestimonial(Long id);
}
