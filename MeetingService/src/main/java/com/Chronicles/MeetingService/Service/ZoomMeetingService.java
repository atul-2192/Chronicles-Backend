package com.Chronicles.MeetingService.Service;


import com.yourcompany.common.dto.MeetingEventDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomMeetingService {

    private final ZoomTokenService tokenService;
    private final RestTemplate restTemplate = new RestTemplate();

    public ZoomMeetingService(ZoomTokenService tokenService) {
        this.tokenService = tokenService;
    }

    public String createMeeting(MeetingEventDTO request) {
        String accessToken = tokenService.getAccessToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String startTime = request.getMeetingAt()
                .atZone(ZoneId.of("Asia/Kolkata"))
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

        Map<String, Object> body = new HashMap<>();
        body.put("topic", "Expert Meeting");
        body.put("type", 2); // Scheduled meeting
        body.put("start_time", startTime); // must be ISO 8601 string like "2025-06-23T10:00:00Z"


        Map<String, Object> settings = new HashMap<>();
        settings.put("host_video", true);
        settings.put("participant_video", true);
        body.put("settings", settings);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity("https://api.zoom.us/v2/users/me/meetings", entity, Map.class);

        return (String) response.getBody().get("join_url");
    }

}

