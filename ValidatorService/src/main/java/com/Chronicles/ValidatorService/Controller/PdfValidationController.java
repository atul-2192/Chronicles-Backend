package com.Chronicles.ValidatorService.Controller;

import com.Chronicles.ValidatorService.Entity.PdfToValidate;
import com.Chronicles.ValidatorService.Service.PdfValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/validate")
public class PdfValidationController {

    @Autowired
    private PdfValidationService service;


    @GetMapping
    public List<PdfToValidate> getPendingPdfs() {
        log.info("Received request to fetch list of unverified PDFs.");
        List<PdfToValidate> pending = service.getPendingPdfs();
        log.info("Returning {} PDFs pending verification.", pending.size());
        return pending;
    }

    @GetMapping("/{emailId}")
    public ResponseEntity<FileSystemResource> viewPdf(@PathVariable String emailId) {
        log.info("Received request to view PDF for email: {}", emailId);

        return service.getPdf(emailId)
                .map(pdf -> {
                    Path path = Paths.get(pdf.getPdfPath());
                    FileSystemResource resource = new FileSystemResource(path.toFile());
                    log.info("Serving PDF file from path: {}", path);
                    return ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_PDF)
                            .body(resource);
                })
                .orElseGet(() -> {
                    log.warn("No PDF found for email: {}", emailId);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping("/verify/{emailId}/{verifierName}")
    public ResponseEntity<String> verifyPdf(
            @PathVariable String emailId,
            @PathVariable String verifierName) {

        log.info("Received verification request for email: {}, by verifier: {}", emailId, verifierName);

        boolean success = service.verifyPdf(emailId, verifierName);

        if (success) {
            log.info("PDF successfully verified for email: {} by {}", emailId, verifierName);
            return ResponseEntity.ok("PDF verified and Kafka messages sent.");
        } else {
            log.warn("Verification failed or already verified for email: {}", emailId);
            return ResponseEntity.badRequest().body("PDF already verified or not found.");
        }
    }
}

