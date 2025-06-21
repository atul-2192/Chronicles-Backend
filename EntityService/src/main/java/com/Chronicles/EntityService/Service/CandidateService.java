package com.Chronicles.EntityService.Service;


import com.Chronicles.EntityService.DTO.ApiResponse;
import com.Chronicles.EntityService.DTO.CandidateDTO;
import com.Chronicles.EntityService.Entity.Candidate;
import com.yourcompany.common.dto.Cargo;

import java.util.List;
import java.util.Map;

public interface CandidateService {

    ApiResponse saveCandidate(Cargo cargo);
    CandidateDTO getCandidateByEmail(String emailId);
    void updateCandidate(String email, Map<String,?> updates);
    List<Candidate> getAllCandidate();
}