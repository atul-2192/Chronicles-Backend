package com.Chronicles.CounsellingService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollegeDTO {

    private String collegeName;
    private String branchName;
    private BigDecimal cutOff;
}
