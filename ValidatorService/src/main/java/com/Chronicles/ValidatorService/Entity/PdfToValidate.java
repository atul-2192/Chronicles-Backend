package com.Chronicles.ValidatorService.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PdfToValidate {
    @Id
    private String emailId;

    private String pdfPath;
    private boolean verified;
    private String verifierName;

    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
}
