package com.yourcompany.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfReadyForValidationEvent {
    private String emailId;
    private String pdfPath;
}

