package com.Chronicles.FeatureService.Kafka.Service;

import com.yourcompany.common.dto.Cargo;
import com.yourcompany.common.dto.EmailNotificationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {
    private final KafkaTemplate<String,Object> kafkaTemplate;

    public void sendToCounselling(Cargo cargo) {
        kafkaTemplate.send("counselling-pdf-topic", cargo);
        log.info("Sent data to counselling-pdf-topic");
    }

    public void sendToEmail(EmailNotificationEvent data) {
        kafkaTemplate.send("email-meeting-topic", data);
        log.info("Sent data to email-meeting-topic");
    }
}
