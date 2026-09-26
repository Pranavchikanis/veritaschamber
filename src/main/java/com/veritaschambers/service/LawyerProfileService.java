package com.veritaschambers.service;

import com.veritaschambers.dto.request.LawyerProfileUpdateRequest;
import com.veritaschambers.dto.response.LawyerProfileResponse;

public interface LawyerProfileService {
    LawyerProfileResponse getLawyerProfile();
    LawyerProfileResponse updateLawyerProfile(LawyerProfileUpdateRequest request);
}
