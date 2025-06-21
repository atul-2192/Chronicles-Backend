package com.Chronicles.CounsellingService.Kafka.DTO;

import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.DTO.LocalCandidate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdfGenerationEvent {
    private LocalCandidate candidate;
    private Map<String, List<CollegeDTO>> collegeLists;
}

