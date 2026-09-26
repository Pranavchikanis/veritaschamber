package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.PracticeAreaCreateRequest;
import com.veritaschambers.dto.request.PracticeAreaUpdateRequest;
import com.veritaschambers.dto.response.PracticeAreaResponse;
import com.veritaschambers.entity.PracticeArea;

public class PracticeAreaMapper {

    private PracticeAreaMapper() {
    }

    public static PracticeArea toEntity(PracticeAreaCreateRequest request) {
        if (request == null) {
            return null;
        }

        PracticeArea entity = new PracticeArea();
        entity.setTitle(request.title());
        entity.setSlug(request.slug());
        entity.setShortDescription(request.shortDescription());
        entity.setDescription(request.description());
        entity.setIconRef(request.iconRef());
        entity.setDisplayOrder(request.displayOrder());
        entity.setStatus(request.status());
        return entity;
    }

    public static void updateEntity(PracticeArea entity, PracticeAreaUpdateRequest request) {
        if (entity == null || request == null) {
            return;
        }

        entity.setTitle(request.title());
        entity.setSlug(request.slug());
        entity.setShortDescription(request.shortDescription());
        entity.setDescription(request.description());
        entity.setIconRef(request.iconRef());
        entity.setDisplayOrder(request.displayOrder());
        entity.setStatus(request.status());
    }

    public static PracticeAreaResponse toResponse(PracticeArea entity) {
        if (entity == null) {
            return null;
        }

        return new PracticeAreaResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getSlug(),
                entity.getShortDescription(),
                entity.getDescription(),
                entity.getIconRef(),
                entity.getDisplayOrder(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
