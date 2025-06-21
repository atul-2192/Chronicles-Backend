package com.Chronicles.FeatureService.Service;


import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.Entity.CatalogFeature;

import java.util.List;

public interface CatalogService {
    void handlePurchase(Cargo request);
    List<CatalogFeature> getAllFeatures();

    CatalogFeature addFeature(CatalogFeature catalogFeature);
}
