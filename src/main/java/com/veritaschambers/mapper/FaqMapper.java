package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.FaqCreateRequest;
import com.veritaschambers.dto.request.FaqUpdateRequest;
import com.veritaschambers.dto.response.FaqResponse;
import com.veritaschambers.entity.Faq;

public class FaqMapper {

    private FaqMapper() {
    }

    public static Faq toEntity(FaqCreateRequest request) {
        if (request == null) return null;

        Faq entity = new Faq();
        entity.setQuestion(request.question());
        entity.setAnswer(request.answer());
        entity.setDisplayOrder(request.displayOrder());
        entity.setIsActive(request.isActive());
        return entity;
    }

    public static void updateEntity(Faq entity, FaqUpdateRequest request) {
        if (entity == null || request == null) return;

        entity.setQuestion(request.question());
        entity.setAnswer(request.answer());
        entity.setDisplayOrder(request.displayOrder());
        entity.setIsActive(request.isActive());
    }

    public static FaqResponse toResponse(Faq entity) {
        if (entity == null) return null;

        return new FaqResponse(
                entity.getId(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getDisplayOrder(),
                entity.getIsActive(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
