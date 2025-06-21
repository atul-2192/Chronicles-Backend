package com.Chronicles.FeatureService.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "catalog_feature")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CatalogFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String featureCode; // e.g., "download", "personalized_sheet", "expert_session"

    private String name;
    private String description;
    private double price;
    private double discount;
    private String imagePath;
    private String buttonAction;
    private String ctaText;
    private boolean visible;
    private int sortOrder;
    private String extraDetails; // optional field for future use
}

