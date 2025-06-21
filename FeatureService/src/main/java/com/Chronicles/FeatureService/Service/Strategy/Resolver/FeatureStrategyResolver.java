package com.Chronicles.FeatureService.Service.Strategy.Resolver;

import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.Service.Strategy.FeatureStrategy;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureStrategyResolver {

    private final Map<String, FeatureStrategy> strategyMap;

    public void handleFeature(Cargo request) {
        String featureCode = request.getFeatureCode();
        FeatureStrategy strategy = strategyMap.get(featureCode);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported feature code: " + featureCode);
        }
        strategy.handle(request);
    }
    @PostConstruct
    public void init() {
        strategyMap.keySet().forEach(code -> log.info("Registered strategy for feature: {}", code));
    }

}

