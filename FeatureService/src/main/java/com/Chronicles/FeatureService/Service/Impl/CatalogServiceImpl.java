package com.Chronicles.FeatureService.Service.Impl;


import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.Entity.CatalogFeature;
import com.Chronicles.FeatureService.Repository.CatalogFeatureRepository;
import com.Chronicles.FeatureService.Service.CatalogService;
import com.Chronicles.FeatureService.Service.Strategy.Resolver.FeatureStrategyResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final FeatureStrategyResolver strategyResolver;
    private final CatalogFeatureRepository featureRepo;

    @Override
    public void handlePurchase(Cargo request) {
        String code = request.getFeatureCode();
//        CatalogFeature feature = featureRepo.findByFeatureCode(code)
//                .orElseThrow(() -> new RuntimeException("Feature not found: " + code));

        log.info("Handling feature {} for {}", code, request.getEmail());
        strategyResolver.handleFeature(request);
    }

    @Override
    public List<CatalogFeature> getAllFeatures() {
        return featureRepo.findAll()
                .stream()
                .filter(CatalogFeature::isVisible)
                .sorted(Comparator.comparingInt(CatalogFeature::getSortOrder))
                .toList();
    }

    @Override
    public CatalogFeature addFeature(CatalogFeature catalogFeature) {
        return featureRepo.save(catalogFeature);
    }
}
