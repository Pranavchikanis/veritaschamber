package com.veritaschambers.service;

import com.veritaschambers.dto.request.WebsiteSettingUpdateRequest;
import com.veritaschambers.dto.response.WebsiteSettingResponse;

public interface WebsiteSettingService {
    WebsiteSettingResponse getWebsiteSettings();
    WebsiteSettingResponse updateWebsiteSettings(WebsiteSettingUpdateRequest request);
}
