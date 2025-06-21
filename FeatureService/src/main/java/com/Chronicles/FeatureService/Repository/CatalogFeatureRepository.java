package com.Chronicles.FeatureService.Repository;

import com.Chronicles.FeatureService.Entity.CatalogFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CatalogFeatureRepository extends JpaRepository<CatalogFeature, Long> {
    Optional<CatalogFeature> findByFeatureCode(String featureCode);
}

