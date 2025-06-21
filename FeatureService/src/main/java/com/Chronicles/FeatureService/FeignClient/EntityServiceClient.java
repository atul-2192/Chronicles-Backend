package com.Chronicles.FeatureService.FeignClient;


import com.yourcompany.common.dto.Cargo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "entity-service",url = "${feign.client.config.entity-service.url}")
public interface EntityServiceClient {
    @PostMapping("/saveDetails")
    void saveEntityData(@RequestBody Cargo cargo);
}

