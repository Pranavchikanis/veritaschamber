package com.veritaschambers.service.impl;

import com.veritaschambers.dto.request.WebsiteSettingUpdateRequest;
import com.veritaschambers.dto.response.WebsiteSettingResponse;
import com.veritaschambers.entity.WebsiteSetting;
import com.veritaschambers.entity.enums.SettingType;
import com.veritaschambers.mapper.WebsiteSettingMapper;
import com.veritaschambers.repository.WebsiteSettingRepository;
import com.veritaschambers.service.WebsiteSettingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebsiteSettingServiceImpl implements WebsiteSettingService {

    private final WebsiteSettingRepository websiteSettingRepository;

    public WebsiteSettingServiceImpl(WebsiteSettingRepository websiteSettingRepository) {
        this.websiteSettingRepository = websiteSettingRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public WebsiteSettingResponse getWebsiteSettings() {
        List<WebsiteSetting> settings = websiteSettingRepository.findAll();
        return WebsiteSettingMapper.toResponse(settings);
    }

    @Override
    @Transactional
    public WebsiteSettingResponse updateWebsiteSettings(WebsiteSettingUpdateRequest request) {
        updateOrSaveSetting("contactEmail", request.contactEmail());
        updateOrSaveSetting("contactPhone", request.contactPhone());
        updateOrSaveSetting("officeAddress", request.officeAddress());
        updateOrSaveSetting("officeHours", request.officeHours());
        updateOrSaveSetting("linkedinUrl", request.linkedinUrl());
        updateOrSaveSetting("twitterUrl", request.twitterUrl());

        List<WebsiteSetting> settings = websiteSettingRepository.findAll();
        return WebsiteSettingMapper.toResponse(settings);
    }

    private void updateOrSaveSetting(String key, String value) {
        WebsiteSetting setting = websiteSettingRepository.findBySettingKey(key);
        if (setting == null) {
            setting = new WebsiteSetting();
            setting.setSettingKey(key);
            setting.setSettingType(SettingType.STRING);
        }
        setting.setSettingValue(value);
        websiteSettingRepository.save(setting);
    }
}
