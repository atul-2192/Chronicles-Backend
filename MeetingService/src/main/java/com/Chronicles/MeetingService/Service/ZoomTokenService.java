package com.Chronicles.MeetingService.Service;

import com.Chronicles.MeetingService.Config.ZoomConfig;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Service
public class ZoomTokenService {

    private final ZoomConfig config;
    private final RestTemplate restTemplate = new RestTemplate();

    public ZoomTokenService(ZoomConfig config) {
        this.config = config;
    }

    public String getAccessToken() {
        String url = "https://zoom.us/oauth/token?grant_type=account_credentials&account_id=" + config.getAccountId();
        String auth = config.getClientId() + ":" + config.getClientSecret();
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Basic " + encodedAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        return (String) response.getBody().get("access_token");
    }
}

