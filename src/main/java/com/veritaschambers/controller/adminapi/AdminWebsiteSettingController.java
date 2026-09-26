package com.veritaschambers.controller.adminapi;

import com.veritaschambers.dto.request.WebsiteSettingUpdateRequest;
import com.veritaschambers.dto.response.WebsiteSettingResponse;
import com.veritaschambers.service.WebsiteSettingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/settings")
public class AdminWebsiteSettingController {

    private final WebsiteSettingService websiteSettingService;

    public AdminWebsiteSettingController(WebsiteSettingService websiteSettingService) {
        this.websiteSettingService = websiteSettingService;
    }

    @GetMapping
    public ResponseEntity<WebsiteSettingResponse> getWebsiteSettings() {
        return ResponseEntity.ok(websiteSettingService.getWebsiteSettings());
    }

    @PutMapping("/{settingKey}")
    public ResponseEntity<WebsiteSettingResponse> updateSetting(
            @PathVariable String settingKey,
            @Valid @RequestBody WebsiteSettingUpdateRequest request) {
        return ResponseEntity.ok(websiteSettingService.updateWebsiteSettings(request));
    }
}
