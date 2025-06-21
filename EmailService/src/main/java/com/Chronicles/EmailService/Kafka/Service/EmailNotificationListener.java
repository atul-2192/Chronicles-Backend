package com.Chronicles.EmailService.Kafka.Service;

import com.Chronicles.EmailService.Service.Impl.EmailService;
import com.yourcompany.common.dto.EmailNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificationListener {

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-notification-topic", groupId = "email-service-group")
    public void listenForPdfNotification(EmailNotificationEvent event) {
        log.info("Received EmailNotificationEvent: emailId={}, pdfPath={}", event.getEmailId(), event.getPdfPath());
        emailService.sendEmailWithAttachment(event.getEmailId(), event.getPdfPath());
    }

    @KafkaListener(topics = "email-meeting-topic", groupId = "email-service-group")
    public void listenForMeetingNotification(EmailNotificationEvent event) {
        log.info("Received EmailNotificationEvent: emailId={}, MeetingRequest", event.getEmailId());
        emailService.sendMeetingEmail(event);
    }

}

