package com.Chronicles.EntityService.Controller;

import com.Chronicles.EntityService.DTO.ApiResponse;
import com.Chronicles.EntityService.DTO.CandidateDTO;
import com.Chronicles.EntityService.Entity.Candidate;
import com.Chronicles.EntityService.Service.CandidateService;
import com.yourcompany.common.dto.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/candidate-details")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/saveDetails")
    public ResponseEntity<ApiResponse> saveCandidateDetails(@RequestBody Cargo cargo)
    {

        ApiResponse apiResponse= candidateService.saveCandidate(cargo);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/getAllCandidates")
    public ResponseEntity<List<Candidate>> getAllCandidateDetails()
    {
        List<Candidate> candidateList= candidateService.getAllCandidate();
        return  new ResponseEntity<>(candidateList,HttpStatus.OK);
    }

    @GetMapping("/getDetails/{emailId}")
    public ResponseEntity<CandidateDTO> getCandidateByEmail(@PathVariable String emailId){
        CandidateDTO candidate= candidateService.getCandidateByEmail(emailId);
        return  new ResponseEntity<>(candidate,HttpStatus.OK);
    }
    @PatchMapping("/update-Candidate/{emailId}")
    public ResponseEntity<Void> updateStatus(@PathVariable String emailId, @RequestBody Map<String, ?> updates) {

        candidateService.updateCandidate(emailId, updates);
        return ResponseEntity.ok().build();
    }
}

