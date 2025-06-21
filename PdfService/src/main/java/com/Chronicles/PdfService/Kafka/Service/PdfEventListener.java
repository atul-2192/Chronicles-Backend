package com.Chronicles.PdfService.Kafka.Service;

import com.Chronicles.PdfService.Kafka.DTO.PdfGenerationEvent;
import com.Chronicles.PdfService.Service.Impl.PdfGeneratorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfEventListener {
    @Autowired
    private PdfGeneratorServiceImpl pdfGeneratorService;

    @KafkaListener(topics = "pdf-generation-topic", groupId = "pdf-service-group")
    public void listen(PdfGenerationEvent event) throws IOException {
        pdfGeneratorService.generateConsolidatedPdf(event.getCandidate(), event.getCollegeLists());
    }
}
