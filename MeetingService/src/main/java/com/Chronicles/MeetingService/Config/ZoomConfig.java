package com.Chronicles.MeetingService.Config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "zoom")
@Data
public class ZoomConfig {
    private String accountId;
    private String clientId;
    private String clientSecret;
}

