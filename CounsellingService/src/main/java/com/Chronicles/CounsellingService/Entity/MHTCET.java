package com.Chronicles.CounsellingService.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class MHTCET {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  int preferenceOrder;
    private String collegeName;
    private String branchName;

    @Column(precision = 10, scale = 7)
    private BigDecimal cutOff;
    private String collegeCode;

}