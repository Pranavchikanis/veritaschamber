package com.veritaschambers.repository;

import com.veritaschambers.entity.WebsiteSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebsiteSettingRepository extends JpaRepository<WebsiteSetting, Long> {
    WebsiteSetting findBySettingKey(String settingKey);
}
