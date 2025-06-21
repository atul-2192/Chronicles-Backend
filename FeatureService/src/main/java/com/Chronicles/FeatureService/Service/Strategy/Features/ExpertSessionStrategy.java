package com.Chronicles.FeatureService.Service.Strategy.Features;


import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.FeignClient.MeetingServiceClient;
import com.Chronicles.FeatureService.Kafka.Service.KafkaProducer;
import com.Chronicles.FeatureService.Service.Strategy.FeatureStrategy;
import com.yourcompany.common.dto.EmailNotificationEvent;
import com.yourcompany.common.dto.MeetingEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component("expert_session")
@RequiredArgsConstructor
public class ExpertSessionStrategy implements FeatureStrategy {

    private final KafkaProducer kafkaProducer;
    private final MeetingServiceClient meetingClient;

    @Override
    public void handle(Cargo request) {
        MeetingEventDTO meetingEventDTO=new MeetingEventDTO(request.getEmail(),request.getName(),
                                        request.getMeetingAt(), request.getQueryDescription());
        String roomId = meetingClient.getMeetingRoomId(meetingEventDTO);

        EmailNotificationEvent emailNotificationEvent= EmailNotificationEvent.builder()
                .name(request.getName())
                .emailId(request.getEmail())
                .meetingAt(request.getMeetingAt())
                .queryDescription(request.getQueryDescription())
                .roomId(roomId)
                .build();
        kafkaProducer.sendToEmail(emailNotificationEvent);

        log.info("Expert session booked for {} with roomId {}", request.getEmail(), roomId);
    }
}
