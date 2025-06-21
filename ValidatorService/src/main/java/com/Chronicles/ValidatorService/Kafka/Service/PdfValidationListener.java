package com.Chronicles.ValidatorService.Kafka.Service;

import com.Chronicles.ValidatorService.Entity.PdfToValidate;
import com.yourcompany.common.dto.PdfReadyForValidationEvent;

import com.Chronicles.ValidatorService.Repository.PdfToValidateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PdfValidationListener {


    @Autowired
    private PdfToValidateRepository repository;

    @KafkaListener(topics = "pdf-validation-topic", groupId = "validation-service-group")
    public void listen(PdfReadyForValidationEvent event) {
        log.info("Received PDF validation event from Kafka - Email: {}, PDF Path: {}",
                event.getEmailId(), event.getPdfPath());

        PdfToValidate pdf = PdfToValidate.builder()
                .emailId(event.getEmailId())
                .pdfPath(event.getPdfPath())
                .verified(false)
                .createdAt(LocalDateTime.now())
                .build();

        repository.save(pdf);

        log.info("PDF validation record saved for Email: {}", event.getEmailId());
    }
}

