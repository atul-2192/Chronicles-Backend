package com.Chronicles.CounsellingService.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class JosaaCsab {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String collegeName;
    private String branchName;

    private int genJosaa;
    private int ewsJosaa;
    private int obcJosaa;
    private int scJosaa;
    private int stJosaa;

    private int genCsab;
    private int ewsCsab;
    private int obcCsab;
    private int scCsab;
    private int stCsab;

}

