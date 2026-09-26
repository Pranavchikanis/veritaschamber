package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.ConsultationCreateRequest;
import com.veritaschambers.dto.response.ConsultationResponse;
import com.veritaschambers.entity.ConsultationRequest;
import com.veritaschambers.entity.enums.ConsultationStatus;

public class ConsultationMapper {

    private ConsultationMapper() {
        // Utility class
    }

    public static ConsultationRequest toEntity(ConsultationCreateRequest request) {
        if (request == null) {
            return null;
        }

        ConsultationRequest entity = new ConsultationRequest();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setPreferredContactMethod(request.preferredContactMethod());
        entity.setSubject(request.subject());
        entity.setMessage(request.message());
        entity.setPreferredDate(request.preferredDate());
        entity.setPreferredTime(request.preferredTime());
        entity.setStatus(ConsultationStatus.NEW);
        return entity;
    }

    public static ConsultationResponse toResponse(ConsultationRequest entity) {
        if (entity == null) {
            return null;
        }

        return new ConsultationResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getPreferredContactMethod(),
                entity.getSubject(),
                entity.getMessage(),
                entity.getPreferredDate(),
                entity.getPreferredTime(),
                entity.getStatus(),
                entity.getAdminNotes(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
