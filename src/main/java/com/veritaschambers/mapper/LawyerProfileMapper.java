package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.LawyerProfileUpdateRequest;
import com.veritaschambers.dto.response.LawyerProfileResponse;
import com.veritaschambers.entity.LawyerProfile;

public class LawyerProfileMapper {

    private LawyerProfileMapper() {
    }

    public static void updateEntity(LawyerProfile entity, LawyerProfileUpdateRequest request) {
        if (entity == null || request == null) return;
        
        entity.setName(request.name());
        entity.setTitle(request.title());
        entity.setBiography(request.biography());
        entity.setCredentials(request.credentials());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setProfileImageUrl(request.profileImageUrl());
    }

    public static LawyerProfileResponse toResponse(LawyerProfile entity) {
        if (entity == null) return null;

        return new LawyerProfileResponse(
                entity.getId(),
                entity.getName(),
                entity.getTitle(),
                entity.getBiography(),
                entity.getCredentials(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getProfileImageUrl(),
                entity.getUpdatedAt()
        );
    }
}
