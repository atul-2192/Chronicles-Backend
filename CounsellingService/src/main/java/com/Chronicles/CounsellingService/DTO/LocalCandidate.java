package com.Chronicles.CounsellingService.DTO;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalCandidate {

    @Id
    private String email;
    private String name;
    private String category;
    private Integer crlRank;
    private Integer categoryRank;
    private BigDecimal mhtCetPercentile;
    private Integer wbJeeRank;
    private Integer comedKRank;
    private Integer ipuCetRank;
    private String homeState;
}

