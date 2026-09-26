package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.LawyerProfileUpdateRequest;
import com.veritaschambers.dto.response.LawyerProfileResponse;
import com.veritaschambers.entity.LawyerProfile;
import com.veritaschambers.exception.ResourceNotFoundException;
import com.veritaschambers.mapper.LawyerProfileMapper;
import com.veritaschambers.repository.LawyerProfileRepository;
import com.veritaschambers.service.LawyerProfileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LawyerProfileServiceImpl implements LawyerProfileService {

    private final LawyerProfileRepository lawyerProfileRepository;

    public LawyerProfileServiceImpl(LawyerProfileRepository lawyerProfileRepository) {
        this.lawyerProfileRepository = lawyerProfileRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public LawyerProfileResponse getLawyerProfile() {
        List<LawyerProfile> profiles = lawyerProfileRepository.findAll();
        if (profiles.isEmpty()) {
            throw new ResourceNotFoundException("Lawyer Profile not found");
        }
        return LawyerProfileMapper.toResponse(profiles.get(0));
    }

    @Override
    @Transactional
    public LawyerProfileResponse updateLawyerProfile(LawyerProfileUpdateRequest request) {
        List<LawyerProfile> profiles = lawyerProfileRepository.findAll();
        LawyerProfile profile;
        if (profiles.isEmpty()) {
            profile = new LawyerProfile();
        } else {
            profile = profiles.get(0);
        }

        LawyerProfileMapper.updateEntity(profile, request);
        LawyerProfile saved = lawyerProfileRepository.save(profile);
        return LawyerProfileMapper.toResponse(saved);
    }
}
