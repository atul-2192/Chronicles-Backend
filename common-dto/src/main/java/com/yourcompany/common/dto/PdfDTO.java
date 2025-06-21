package com.yourcompany.common.dto;

public class PdfDTO {
    private String email;
    private String pdfPath;

    public PdfDTO() {}

    public PdfDTO(String email, String pdfPath) {
        this.email = email;
        this.pdfPath = pdfPath;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }
}
