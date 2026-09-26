package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.ContactMessageCreateRequest;
import com.veritaschambers.dto.response.ContactMessageResponse;
import com.veritaschambers.entity.ContactMessage;
import com.veritaschambers.entity.enums.MessageStatus;

public class ContactMessageMapper {

    private ContactMessageMapper() {
        // Utility class
    }

    public static ContactMessage toEntity(ContactMessageCreateRequest request) {
        if (request == null) {
            return null;
        }

        ContactMessage entity = new ContactMessage();
        entity.setName(request.name());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setSubject(request.subject());
        entity.setMessage(request.message());
        entity.setStatus(MessageStatus.NEW);
        return entity;
    }

    public static ContactMessageResponse toResponse(ContactMessage entity) {
        if (entity == null) {
            return null;
        }

        return new ContactMessageResponse(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getSubject(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
