package com.Chronicles.CounsellingService.Kafka.Service;

import com.Chronicles.CounsellingService.Kafka.DTO.PdfGenerationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PdfEventPublisher {
    @Autowired
    private KafkaTemplate<String, PdfGenerationEvent> kafkaTemplate;

    public void sendPdfGenerationEvent(PdfGenerationEvent event) {
        kafkaTemplate.send("pdf-generation-topic", event);
    }
}

