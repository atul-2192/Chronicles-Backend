package com.Chronicles.ValidatorService.Repository;

import com.Chronicles.ValidatorService.Entity.PdfToValidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PdfToValidateRepository extends JpaRepository<PdfToValidate, String> {
    List<PdfToValidate> findByVerifiedFalse();
}

