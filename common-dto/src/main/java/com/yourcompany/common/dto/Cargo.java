package com.yourcompany.common.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Cargo {
    private String name;
    private String email;
    private String featureCode;

    private BigDecimal jeePercentile;
    private String category;
    private Integer crlRank;
    private Integer categoryRank;
    private BigDecimal mhtCetPercentile;
    private Integer wbJeeRank;
    private Integer comedKRank;
    private Integer ipuCetRank;
    private String homeState;
    private String contactNumber;

    private LocalDateTime meetingAt;
    private String QueryDescription;
}

