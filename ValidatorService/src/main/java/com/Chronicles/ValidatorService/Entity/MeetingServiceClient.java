package com.Chronicles.ValidatorService.Entity;

import com.yourcompany.common.dto.MeetingEventDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@FeignClient(name = "meeting-service",url = "${feign.client.config.meeting-service.url}")
public interface MeetingServiceClient {
    @PostMapping("/create-meeting")
    String getMeetingRoomId(@RequestBody MeetingEventDTO meetingEventDTO);
}


