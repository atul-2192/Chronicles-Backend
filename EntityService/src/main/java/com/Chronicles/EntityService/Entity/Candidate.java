package com.Chronicles.EntityService.Entity;

import com.Chronicles.EntityService.DTO.PlanType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candidate {

    @Id
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contactNumber;

    private String category;
    private Integer crlRank;
    private Integer categoryRank;
    private BigDecimal mhtCetPercentile;
    private Integer wbJeeRank;
    private Integer comedKRank;
    private Integer ipuCetRank;
    private List<String> branchPreferences;
    private String homeState;
    private List<String> collegePreference;
    private String planType;

    private String pdfPath;
    private String signedBy;
    private boolean pdfGenerated;
    private boolean pdfVerified;
    private boolean pdfSent;
    private boolean feedback;

}

