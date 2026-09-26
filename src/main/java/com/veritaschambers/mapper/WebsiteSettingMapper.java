package com.veritaschambers.mapper;

import com.veritaschambers.dto.request.WebsiteSettingUpdateRequest;
import com.veritaschambers.dto.response.WebsiteSettingResponse;
import com.veritaschambers.entity.WebsiteSetting;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WebsiteSettingMapper {

    private WebsiteSettingMapper() {
    }

    public static WebsiteSettingResponse toResponse(List<WebsiteSetting> settings) {
        if (settings == null || settings.isEmpty()) {
            return new WebsiteSettingResponse(null, null, null, null, null, null, null, null);
        }

        Map<String, String> map = settings.stream()
                .collect(Collectors.toMap(
                        WebsiteSetting::getSettingKey, 
                        s -> s.getSettingValue() != null ? s.getSettingValue() : ""
                ));

        return new WebsiteSettingResponse(
                null,
                map.get("contactEmail"),
                map.get("contactPhone"),
                map.get("officeAddress"),
                map.get("officeHours"),
                map.get("linkedinUrl"),
                map.get("twitterUrl"),
                settings.get(0).getUpdatedAt() // Just picking the first one
        );
    }
}
