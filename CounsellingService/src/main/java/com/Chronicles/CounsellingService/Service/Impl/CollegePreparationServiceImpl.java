package com.Chronicles.CounsellingService.Service.Impl;

import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.DTO.LocalCandidate;
import com.Chronicles.CounsellingService.Repository.*;
import com.Chronicles.CounsellingService.Service.CollegePreparationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CollegePreparationServiceImpl implements CollegePreparationService {

    @Autowired private ComedKRepository comedKRepository;
    @Autowired private GGSIPURepository ggsipuRepository;
    @Autowired private JacChandigarhRepository jacChandigarhRepository;
    @Autowired private JacDelhiRepository jacDelhiRepository;
    @Autowired private MHTCETRepository mhtcetRepository;
    @Autowired private UPTACRepository uptacRepository;
    @Autowired private WBJEERepository wbjeeRepository;
    @Autowired private JosaaCsabRepository josaaCsabRepository;
    @Autowired private HomeStateRepository homeStateRepository;

    @Async
    @Override
    public CompletableFuture<List<ComedK>> getComedKList(LocalCandidate candidate) {
        if (candidate.getComedKRank() == null) {
            log.info("No ComedK rank provided for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        long cutOff = Math.max(0, candidate.getComedKRank() - 2000);
        log.debug("Fetching ComedK colleges for cutoff: {}", cutOff);
        return CompletableFuture.completedFuture(
                comedKRepository.findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(cutOff));
    }

    @Async
    @Override
    public CompletableFuture<List<GGSIPU>> getGgsipuList(LocalCandidate candidate) {
        if (candidate.getIpuCetRank() == null) {
            log.info("No IPU CET rank provided for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        long cutOff = candidate.getIpuCetRank() - 1000;
        log.debug("Fetching GGSIPU colleges for cutoff: {}", cutOff);
        return CompletableFuture.completedFuture(
                ggsipuRepository.findTop50ByCutOffOutSideDelhiGreaterThanEqualOrderByCutOffOutSideDelhiAsc(cutOff));
    }

    @Async
    @Override
    public CompletableFuture<List<MHTCET>> getMhtcetList(LocalCandidate candidate) {
        if (candidate.getMhtCetPercentile() == null) {
            log.info("No MHT-CET percentile provided for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        BigDecimal cutOff = candidate.getMhtCetPercentile().add(BigDecimal.valueOf(2));
        log.debug("Fetching MHTCET colleges for cutoff: {}", cutOff);
        return CompletableFuture.completedFuture(
                mhtcetRepository.findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(cutOff));
    }

    @Async
    @Override
    public CompletableFuture<List<WBJEE>> getWbjeeList(LocalCandidate candidate) {
        if (candidate.getWbJeeRank() == null) {
            log.info("No WBJEE rank provided for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        int cutOff = candidate.getWbJeeRank() >= 10000
                ? candidate.getWbJeeRank() - 5000
                : candidate.getWbJeeRank() - 2000;

        log.debug("Fetching WBJEE colleges for cutoff: {}", cutOff);
        return CompletableFuture.completedFuture(
                wbjeeRepository.findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(cutOff));
    }

    @Async
    @Override
    public CompletableFuture<List<JacChandigarh>> getJacChandigarhList(LocalCandidate candidate) {
        if (candidate.getCrlRank() == null) {
            log.info("No CRL rank provided for JacChandigarh for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        log.debug("Fetching JacChandigarh colleges for cutoff: {}", candidate.getCrlRank());
        return CompletableFuture.completedFuture(
                jacChandigarhRepository.findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(candidate.getCrlRank()));
    }

    @Async
    @Override
    public CompletableFuture<List<JacDelhi>> getJacDelhiList(LocalCandidate candidate) {
        log.debug("Fetching all JacDelhi colleges for candidate: {}", candidate.getEmail());
        return CompletableFuture.completedFuture(jacDelhiRepository.findAll());
    }

    @Async
    @Override
    public CompletableFuture<List<UPTAC>> getUptacList(LocalCandidate candidate) {
        if (candidate.getCrlRank() == null) {
            log.info("No CRL rank provided for UPTAC for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        int cutOff = calculateDynamicCutoff(candidate.getCrlRank());
        log.debug("Fetching UPTAC colleges for cutoff: {}", cutOff);
        return CompletableFuture.completedFuture(
                uptacRepository.findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(cutOff));
    }

    @Async
    @Override
    public CompletableFuture<List<CollegeDTO>> getJosaaList(LocalCandidate candidate) {
        return getCollegeDTOs(candidate, true);
    }

    @Async
    @Override
    public CompletableFuture<List<CollegeDTO>> getCsabList(LocalCandidate candidate) {
        return getCollegeDTOs(candidate, false);
    }

    @Async
    @Override
    public CompletableFuture<List<HomeState>> getHomeStateList(LocalCandidate candidate) {
        if (candidate.getCrlRank() == null) {
            log.info("No CRL rank provided for HomeState for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        int cutOff = calculateDynamicCutoff(candidate.getCrlRank());
        log.debug("Fetching HomeState colleges for cutoff: {}", cutOff);

        List<HomeState> result = homeStateRepository.findByCutOffGreaterThanEqual(cutOff).stream()
                .sorted(Comparator.comparing(HomeState::getCutOff))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(result);
    }

    private CompletableFuture<List<CollegeDTO>> getCollegeDTOs(LocalCandidate candidate, boolean isJosaa) {
        if (candidate.getCategory() == null || candidate.getCrlRank() == null) {
            log.info("Missing category or CRL/category rank for candidate: {}", candidate.getEmail());
            return emptyFuture();
        }

        int cutoff = calculateDynamicCutoff(
                isJosaa ? candidate.getCategoryRank() : candidate.getCrlRank());

        String category = candidate.getCategory();
        log.debug("Fetching {} colleges for category: {}, cutoff: {}", isJosaa ? "JoSAA" : "CSAB", category, cutoff);

        List<JosaaCsab> list;

        switch (category) {
            case "GEN":
                list = isJosaa
                        ? josaaCsabRepository.findTop100ByGenJosaaGreaterThanEqualOrderByGenJosaaAsc(cutoff)
                        : josaaCsabRepository.findTop100ByGenCsabGreaterThanEqualOrderByGenCsabAsc(cutoff);
                return completedFutureDTO(list, isJosaa ? JosaaCsab::getGenJosaa : JosaaCsab::getGenCsab);
            case "EWS":
                list = isJosaa
                        ? josaaCsabRepository.findTop100ByEwsJosaaGreaterThanEqualOrderByEwsJosaaAsc(cutoff)
                        : josaaCsabRepository.findTop100ByEwsCsabGreaterThanEqualOrderByEwsCsabAsc(cutoff);
                return completedFutureDTO(list, isJosaa ? JosaaCsab::getEwsJosaa : JosaaCsab::getEwsCsab);
            case "OBC":
                list = isJosaa
                        ? josaaCsabRepository.findTop100ByObcJosaaGreaterThanEqualOrderByObcJosaaAsc(cutoff)
                        : josaaCsabRepository.findTop100ByObcCsabGreaterThanEqualOrderByObcCsabAsc(cutoff);
                return completedFutureDTO(list, isJosaa ? JosaaCsab::getObcJosaa : JosaaCsab::getObcCsab);
            case "SC":
                list = isJosaa
                        ? josaaCsabRepository.findTop100ByScJosaaGreaterThanEqualOrderByScJosaaAsc(cutoff)
                        : josaaCsabRepository.findTop100ByScCsabGreaterThanEqualOrderByScCsabAsc(cutoff);
                return completedFutureDTO(list, isJosaa ? JosaaCsab::getScJosaa : JosaaCsab::getScCsab);
            case "ST":
                list = isJosaa
                        ? josaaCsabRepository.findTop100ByStJosaaGreaterThanEqualOrderByStJosaaAsc(cutoff)
                        : josaaCsabRepository.findTop100ByStCsabGreaterThanEqualOrderByStCsabAsc(cutoff);
                return completedFutureDTO(list, isJosaa ? JosaaCsab::getStJosaa : JosaaCsab::getStCsab);
            default:
                log.warn("Unsupported category: {}", category);
                return emptyFuture();
        }
    }

    private <T> CompletableFuture<List<T>> emptyFuture() {
        return CompletableFuture.completedFuture(Collections.emptyList());
    }

    private int calculateDynamicCutoff(int base) {
        return base - (base * 15) / 100;
    }

    private CompletableFuture<List<CollegeDTO>> completedFutureDTO(List<JosaaCsab> list, Function<JosaaCsab, Integer> cutoffExtractor) {
        return CompletableFuture.completedFuture(
                list.stream()
                        .map(college -> new CollegeDTO(college.getCollegeName(), college.getBranchName(), BigDecimal.valueOf(cutoffExtractor.apply(college))))
                        .collect(Collectors.toList()));
    }
}
