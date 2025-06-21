package com.Chronicles.EntityService.Service.Impl;

import com.Chronicles.EntityService.DTO.ApiResponse;
import com.Chronicles.EntityService.DTO.CandidateDTO;
import com.Chronicles.EntityService.Entity.Candidate;
import com.Chronicles.EntityService.Repository.CandidateRepository;
import com.Chronicles.EntityService.Service.CandidateService;
import com.yourcompany.common.dto.Cargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class CandidateServiceImpl implements CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;


    public ApiResponse saveCandidate(Cargo cargo) {
        Candidate candidate= Candidate.builder()
                .name(cargo.getName())
                .email(cargo.getEmail())
                .contactNumber(cargo.getContactNumber())
                .category(cargo.getCategory())
                .crlRank(cargo.getCrlRank())
                .categoryRank(cargo.getCategoryRank())
                .mhtCetPercentile(cargo.getMhtCetPercentile())
                .wbJeeRank(cargo.getWbJeeRank())
                .comedKRank(cargo.getComedKRank())
                .ipuCetRank(cargo.getIpuCetRank())
                .homeState(cargo.getHomeState())
                .planType(cargo.getFeatureCode())
                .build();

        Candidate savedCandidate = candidateRepository.save(candidate);
        return new ApiResponse("Candidate saved ", true);
    }

    @Override
    public CandidateDTO getCandidateByEmail(String emailId) {
        Candidate candidate= candidateRepository.findById(emailId)
                .orElseThrow(() -> new RuntimeException("Candidate not found with given Email ID: " + emailId));
        CandidateDTO candidateDTO=new CandidateDTO(candidate.getEmail(), candidate.getName(),
               candidate.getCategory(), candidate.getCrlRank(),
                candidate.getCategoryRank(), candidate.getMhtCetPercentile(), candidate.getWbJeeRank(),
                candidate.getComedKRank(), candidate.getIpuCetRank(), candidate.getHomeState(),candidate.getPlanType());
    return candidateDTO;
    }

    @Override
    public void updateCandidate(String email, Map<String, ?> updates) {
        Candidate candidate = candidateRepository.findById(email)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if(updates.containsKey("Pdf_Path"))
            candidate.setPdfPath((String) updates.get("Pdf_Path"));
        if(updates.containsKey("SignedBy"))
            candidate.setSignedBy((String) updates.get("SignedBy"));
        if(updates.containsKey("Pdf_Generated"))
            candidate.setPdfGenerated((Boolean) updates.get("Pdf_Generated"));
        if(updates.containsKey("Pdf_Verified"))
            candidate.setPdfVerified((Boolean) updates.get("Pdf_Verified"));
        if(updates.containsKey("Pdf_Sent"))
            candidate.setPdfPath((String) updates.get("Pdf_Sent"));

        candidateRepository.save(candidate);
    }


    @Override
    public List<Candidate> getAllCandidate() {
        return candidateRepository.findAll();
    }

}