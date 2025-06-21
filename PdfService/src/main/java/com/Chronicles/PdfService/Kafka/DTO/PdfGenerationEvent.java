package com.Chronicles.PdfService.Kafka.DTO;

import com.Chronicles.PdfService.DTO.Candidate;
import com.Chronicles.PdfService.DTO.CollegeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfGenerationEvent {
    private Candidate candidate;
    private Map<String, List<CollegeDTO>> collegeLists;
}