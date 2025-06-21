package com.Chronicles.MeetingService.Controller;


import com.Chronicles.MeetingService.Service.ZoomMeetingService;
import com.yourcompany.common.dto.MeetingEventDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/zoom")
public class ZoomController {

    private final ZoomMeetingService meetingService;

    public ZoomController(ZoomMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping("/create-meeting")
    public ResponseEntity< String> createMeeting(@RequestBody MeetingEventDTO meetingEventDTO) {
        String joinUrl = meetingService.createMeeting(meetingEventDTO);
        return ResponseEntity.ok( joinUrl);
    }
}


