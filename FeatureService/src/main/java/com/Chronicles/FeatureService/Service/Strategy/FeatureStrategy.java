package com.Chronicles.FeatureService.Service.Strategy;


import com.yourcompany.common.dto.Cargo;

public interface FeatureStrategy {
    void handle(Cargo request);
}
