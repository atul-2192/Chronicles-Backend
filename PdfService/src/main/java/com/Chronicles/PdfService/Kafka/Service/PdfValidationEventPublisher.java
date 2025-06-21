package com.Chronicles.PdfService.Kafka.Service;


import com.yourcompany.common.dto.PdfReadyForValidationEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PdfValidationEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(PdfValidationEventPublisher.class);

    @Autowired
    private KafkaTemplate<String, PdfReadyForValidationEvent> kafkaTemplate;

    public void sendValidationEvent(PdfReadyForValidationEvent event) {
        logger.info("Attempting to publish PDF validation event for candidate: {}, PDF path: {}",
                event.getEmailId(), event.getPdfPath());

        kafkaTemplate.send("pdf-validation-topic", event);

    }
}

