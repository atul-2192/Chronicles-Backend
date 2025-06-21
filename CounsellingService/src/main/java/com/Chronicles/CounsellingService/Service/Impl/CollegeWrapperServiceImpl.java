package com.Chronicles.CounsellingService.Service.Impl;

import com.Chronicles.CounsellingService.Adapters.AdapterRegistry;
import com.Chronicles.CounsellingService.DTO.ApiResponse;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.Kafka.DTO.PdfGenerationEvent;
import com.Chronicles.CounsellingService.DTO.LocalCandidate;
import com.Chronicles.CounsellingService.Kafka.Service.PdfEventPublisher;
import com.Chronicles.CounsellingService.Service.CollegePreparationService;
import com.Chronicles.CounsellingService.Service.CollegeWrapperService;
import com.yourcompany.common.dto.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * Service implementation to wrap all eligible colleges for a candidate
 * and publish a PDF generation event.
 */
@Service
public class CollegeWrapperServiceImpl implements CollegeWrapperService {




    private static final Logger logger = LoggerFactory.getLogger(CollegeWrapperServiceImpl.class);

    private final CollegePreparationService collegePreparationService;
    private final PdfEventPublisher pdfEventPublisher;
    private final AdapterRegistry adapterRegistry;

    public CollegeWrapperServiceImpl(
                                     CollegePreparationService collegePreparationService,
                                     PdfEventPublisher pdfEventPublisher , AdapterRegistry adapterRegistry) {

        this.collegePreparationService = collegePreparationService;
        this.pdfEventPublisher = pdfEventPublisher;
        this.adapterRegistry=adapterRegistry;
    }

    @Override
    public ApiResponse wrapColleges(Cargo cargo) {
        logger.info("Starting college wrapping for candidate: {}", cargo.getName());

       LocalCandidate candidateOpt=new LocalCandidate(cargo.getEmail(),cargo.getName(),cargo.getCategory(),cargo.getCrlRank(),cargo.getCategoryRank(),cargo.getMhtCetPercentile(),cargo.getWbJeeRank(),cargo.getComedKRank(),cargo.getIpuCetRank(),cargo.getHomeState());


        Map<String, List<CollegeDTO>> collegeList = getCollegeList(candidateOpt);
        if (collegeList.isEmpty()) {
            logger.warn("Generated college list is empty for candidate: {}", candidateOpt.getName());
            return new ApiResponse("College list could not be generated", false);
        }

        try {
            PdfGenerationEvent event = new PdfGenerationEvent(candidateOpt, collegeList);
            pdfEventPublisher.sendPdfGenerationEvent(event);
            logger.info("Successfully published PDF generation event for candidate: {}", candidateOpt.getName());
            return new ApiResponse("Colleges generated and event emitted to PDF Microservice", true);
        } catch (Exception e) {
            logger.error("Failed to publish PDF generation event for candidate: {}",  candidateOpt.getName(), e);
            return new ApiResponse("Failed to send PDF generation event", false);
        }
    }

    private Map<String, List<CollegeDTO>> getCollegeList(LocalCandidate candidate) {
        logger.debug("Fetching college lists for candidate: {}", candidate.getEmail());

        CompletableFuture<List<ComedK>> comedKFuture = collegePreparationService.getComedKList(candidate);
        CompletableFuture<List<GGSIPU>> ggsipuFuture = collegePreparationService.getGgsipuList(candidate);
        CompletableFuture<List<MHTCET>> mhtcetFuture = collegePreparationService.getMhtcetList(candidate);
        CompletableFuture<List<WBJEE>> wbjeeFuture = collegePreparationService.getWbjeeList(candidate);
        CompletableFuture<List<JacChandigarh>> jacChandigarhFuture = collegePreparationService.getJacChandigarhList(candidate);
        CompletableFuture<List<JacDelhi>> jacDelhiFuture = collegePreparationService.getJacDelhiList(candidate);
        CompletableFuture<List<UPTAC>> uptacFuture = collegePreparationService.getUptacList(candidate);
        CompletableFuture<List<CollegeDTO>> josaaFuture = collegePreparationService.getJosaaList(candidate);
        CompletableFuture<List<CollegeDTO>> csabFuture = collegePreparationService.getCsabList(candidate);
        CompletableFuture<List<HomeState>> homeStateFuture = collegePreparationService.getHomeStateList(candidate);

        CompletableFuture.allOf(
                comedKFuture, ggsipuFuture, mhtcetFuture, wbjeeFuture,
                jacChandigarhFuture, jacDelhiFuture, uptacFuture,
                josaaFuture, csabFuture, homeStateFuture
        ).join();

        Map<String, List<?>> collegeLists = new HashMap<>();
        try {
            collegeLists.put("ComedK", comedKFuture.join());
            collegeLists.put("GGSIPU", ggsipuFuture.join());
            collegeLists.put("MHTCET", mhtcetFuture.join());
            collegeLists.put("WBJEE", wbjeeFuture.join());
            collegeLists.put("JacChandigarh", jacChandigarhFuture.join());
            collegeLists.put("JacDelhi", jacDelhiFuture.join());
            collegeLists.put("UPTAC", uptacFuture.join());
            collegeLists.put("Josaa", josaaFuture.join());
            collegeLists.put("Csab", csabFuture.join());
            collegeLists.put("HomeState", homeStateFuture.join());
        } catch (Exception e) {
            logger.error("Exception while collecting college lists", e);
        }

        return convertToDto(collegeLists);
    }

    private Map<String, List<CollegeDTO>> convertToDto(Map<String, List<?>> collegeLists) {
        Map<String, List<CollegeDTO>> dtoLists=new HashMap<>();

        dtoLists.put("ComedK", adapterRegistry.convert(collegeLists.get("ComedK")));
        dtoLists.put("GGSIPU", adapterRegistry.convert(collegeLists.get("GGSIPU")));
        dtoLists.put("MHTCET", adapterRegistry.convert(collegeLists.get("MHTCET")));
        dtoLists.put("WBJEE", adapterRegistry.convert(collegeLists.get("WBJEE")));
        dtoLists.put("JacChandigarh", adapterRegistry.convert(collegeLists.get("JacChandigarh")));
        dtoLists.put("JacDelhi", adapterRegistry.convert(collegeLists.get("JacDelhi")));
        dtoLists.put("UPTAC", adapterRegistry.convert(collegeLists.get("UPTAC")));
        dtoLists.put("Josaa", (List<CollegeDTO>) collegeLists.get("Josaa"));
        dtoLists.put("Csab", (List<CollegeDTO>) collegeLists.get("Csab"));
        dtoLists.put("HomeState", adapterRegistry.convert(collegeLists.get("HomeState")));
        return dtoLists;
    }

}
