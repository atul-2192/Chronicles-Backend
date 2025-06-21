package com.Chronicles.CounsellingService.Kafka.Service;

import com.Chronicles.CounsellingService.Service.CollegeWrapperService;
import com.yourcompany.common.dto.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CandidateEventListener {

    @Autowired
    private CollegeWrapperService collegeWrapperService;

    @KafkaListener(topics = "counselling-pdf-topic", groupId = "counselling-group")
    public void listen(Cargo event) {
        System.out.println("Received event: " + event);
        collegeWrapperService.wrapColleges(event);
    }
}
