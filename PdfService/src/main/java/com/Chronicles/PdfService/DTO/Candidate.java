package com.Chronicles.PdfService.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Candidate {

    private String email;
    private String name;
    private BigDecimal jeePercentile;
    private String category;
    private Integer crlRank;
    private Integer categoryRank;
    private BigDecimal mhtCetPercentile;
    private Integer wbJeeRank;
    private Integer comedKRank;
    private Integer ipuCetRank;
    private String homeState;
}
