package com.Chronicles.FeatureService.Service.Strategy.Features;

import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.Service.Strategy.FeatureStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("download")
public class DownloadStrategy implements FeatureStrategy {

    @Override
    public void handle(Cargo request) {
        log.info("User {} triggered Download feature.", request.getEmail());
        // You can track downloads here, or no-op
    }
}
