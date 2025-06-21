package com.Chronicles.FeatureService.Service.Strategy.Features;


import com.yourcompany.common.dto.Cargo;
import com.Chronicles.FeatureService.FeignClient.EntityServiceClient;
import com.Chronicles.FeatureService.Kafka.Service.KafkaProducer;
import com.Chronicles.FeatureService.Service.Strategy.FeatureStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("personalized_sheet")
@RequiredArgsConstructor
public class PersonalizedSheetStrategy implements FeatureStrategy {

    private final KafkaProducer kafkaProducer;
    private final EntityServiceClient entityClient;

    @Override
    public void handle(Cargo request) {
        log.info("Processing personalized sheet for {}", request.getEmail());

        entityClient.saveEntityData(request);
        kafkaProducer.sendToCounselling(request);

    }
}

