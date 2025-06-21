package com.Chronicles.ValidatorService.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "entity-service",url = "${feign.client.config.entity-service.url}")
public interface EntityServiceClient {
    @PatchMapping("/update-Candidate/{emailId}")
    void updateStatus(@PathVariable("emailId") String emailId, @RequestBody Map<String, Object> updates);



}

