package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.TestimonialCreateRequest;
import com.veritaschambers.dto.request.TestimonialUpdateRequest;
import com.veritaschambers.dto.response.TestimonialResponse;
import com.veritaschambers.entity.Testimonial;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.TestimonialMapper;
import com.veritaschambers.repository.TestimonialRepository;
import com.veritaschambers.service.TestimonialService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestimonialServiceImpl implements TestimonialService {

    private final TestimonialRepository testimonialRepository;

    public TestimonialServiceImpl(TestimonialRepository testimonialRepository) {
        this.testimonialRepository = testimonialRepository;
    }

    @Override
    @Transactional
    public TestimonialResponse createTestimonial(TestimonialCreateRequest request) {
        Testimonial entity = TestimonialMapper.toEntity(request);
        Testimonial saved = testimonialRepository.save(entity);
        return TestimonialMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public TestimonialResponse updateTestimonial(Long id, TestimonialUpdateRequest request) {
        Testimonial entity = testimonialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));
        
        TestimonialMapper.updateEntity(entity, request);
        Testimonial updated = testimonialRepository.save(entity);
        return TestimonialMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TestimonialResponse> getAllTestimonials(Pageable pageable) {
        return testimonialRepository.findAll(pageable)
                .map(TestimonialMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public java.util.List<TestimonialResponse> getPublishedTestimonials() {
        return testimonialRepository.findAll().stream()
                .filter(t -> t.getStatus() == com.veritaschambers.entity.enums.ContentStatus.PUBLISHED)
                .map(TestimonialMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TestimonialResponse getTestimonialById(Long id) {
        return testimonialRepository.findById(id)
                .map(TestimonialMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Testimonial not found"));
    }

    @Override
    @Transactional
    public void deleteTestimonial(Long id) {
        if (!testimonialRepository.existsById(id)) {
            throw new ResourceNotFoundException("Testimonial not found");
        }
        testimonialRepository.deleteById(id);
    }
}
