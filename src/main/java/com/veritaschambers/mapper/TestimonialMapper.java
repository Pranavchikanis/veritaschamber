package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.TestimonialCreateRequest;
import com.veritaschambers.dto.request.TestimonialUpdateRequest;
import com.veritaschambers.dto.response.TestimonialResponse;
import com.veritaschambers.entity.Testimonial;

public class TestimonialMapper {

    private TestimonialMapper() {
    }

    public static Testimonial toEntity(TestimonialCreateRequest request) {
        if (request == null) return null;

        Testimonial entity = new Testimonial();
        entity.setClientName(request.clientName());
        entity.setDesignation(request.clientTitle());
        entity.setTestimonialText(request.content());
        entity.setDisplayOrder(request.displayOrder());
        entity.setStatus(request.status());
        return entity;
    }

    public static void updateEntity(Testimonial entity, TestimonialUpdateRequest request) {
        if (entity == null || request == null) return;

        entity.setClientName(request.clientName());
        entity.setDesignation(request.clientTitle());
        entity.setTestimonialText(request.content());
        entity.setDisplayOrder(request.displayOrder());
        entity.setStatus(request.status());
    }

    public static TestimonialResponse toResponse(Testimonial entity) {
        if (entity == null) return null;

        return new TestimonialResponse(
                entity.getId(),
                entity.getClientName(),
                entity.getDesignation(),
                entity.getTestimonialText(),
                entity.getDisplayOrder(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
