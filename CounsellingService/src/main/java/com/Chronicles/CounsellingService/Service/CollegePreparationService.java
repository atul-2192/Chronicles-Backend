package com.Chronicles.CounsellingService.Service;

import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.DTO.LocalCandidate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CollegePreparationService {
    CompletableFuture<List<ComedK>> getComedKList(LocalCandidate candidate);
    CompletableFuture<List<GGSIPU>> getGgsipuList(LocalCandidate candidate);
    CompletableFuture<List<MHTCET>> getMhtcetList(LocalCandidate candidate);
    CompletableFuture<List<WBJEE>> getWbjeeList(LocalCandidate candidate);
    CompletableFuture<List<JacChandigarh>> getJacChandigarhList(LocalCandidate candidate);
    CompletableFuture<List<JacDelhi>> getJacDelhiList(LocalCandidate candidate);
    CompletableFuture<List<UPTAC>> getUptacList(LocalCandidate candidate);
    CompletableFuture<List<CollegeDTO>> getJosaaList(LocalCandidate candidate);
    CompletableFuture<List<CollegeDTO>> getCsabList(LocalCandidate candidate);
    CompletableFuture<List<HomeState>> getHomeStateList(LocalCandidate candidate);
}
