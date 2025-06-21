package com.yourcompany.common.dto;

public class ValidationDTO {
    private String email;
    private String pdfPath;
    private String verifierName;

    public ValidationDTO() {}

    public ValidationDTO(String email, String pdfPath, String verifierName) {
        this.email = email;
        this.pdfPath = pdfPath;
        this.verifierName = verifierName;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPdfPath() { return pdfPath; }
    public void setPdfPath(String pdfPath) { this.pdfPath = pdfPath; }

    public String getVerifierName() { return verifierName; }
    public void setVerifierName(String verifierName) { this.verifierName = verifierName; }
}
