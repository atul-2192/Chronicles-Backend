package com.Chronicles.ValidatorService.Kafka.Service;

import com.yourcompany.common.dto.EmailNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PdfValidationEventPublisher {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void publishValidationSuccess(EmailNotificationEvent emailNotificationEvent) {




        log.info("Publishing EmailNotificationEvent to 'email-notification-topic' for email: {}", emailNotificationEvent.getEmailId());
        kafkaTemplate.send("email-notification-topic", emailNotificationEvent);
        log.info("Published EmailNotificationEvent for email: {}", emailNotificationEvent.getEmailId());
    }
}


