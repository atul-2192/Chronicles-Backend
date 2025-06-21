package com.Chronicles.EmailService.Feign;

import com.Chronicles.EmailService.DTO.CandidateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;


@FeignClient(name = "entity-service",url = "${feign.client.config.entity-service.url}")
public interface EntityServiceClient {

    @GetMapping("/getDetails/{emailId}")
    CandidateDTO getCandidateByEmail(@PathVariable String emailId);

    @PatchMapping("/update-Candidate/{emailId}")
    void updateStatus(@PathVariable("emailId") String emailId, @RequestBody Map<String, Object> updates);



}