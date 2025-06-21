package com.Chronicles.CounsellingService.Service.Impl;

import com.Chronicles.CounsellingService.DTO.ApiResponse;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.Repository.*;
import com.Chronicles.CounsellingService.Service.DataMigrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataMigrationServiceImpl implements DataMigrationService {

    private static final Logger log = LoggerFactory.getLogger(DataMigrationServiceImpl.class);

    @Autowired
    ComedKRepository comedKRepository;
    @Autowired
    GGSIPURepository ggsipuRepository;
    @Autowired
    JacChandigarhRepository jacChandigarhRepository;
    @Autowired
    JacDelhiRepository jacDelhiRepository;
    @Autowired
    MHTCETRepository mhtcetRepository;
    @Autowired
    UPTACRepository uptacRepository;
    @Autowired
    WBJEERepository wbjeeRepository;
    @Autowired
    JosaaCsabRepository josaaCsabRepository;
    @Autowired
    HomeStateRepository homeStateRepository;

    @Override
    public ApiResponse migrateComedK(List<ComedK> comedKList) {

        try{
            comedKList.forEach(comedK -> {
                comedKRepository.save(comedK);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of ComedK List has been Failed!!", false);
        }

        return new ApiResponse("Migration of ComedK List has been successfully Completed!!", true);
    }

    @Override
    public ApiResponse migrateGGSIPU(List<GGSIPU> GGSIPUList) {
        try{
            GGSIPUList.forEach(ggsipu -> {
                ggsipuRepository.save(ggsipu);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of GGSIPU List has been Failed!!", false);
        }

        return new ApiResponse("Migration of GGSIPU List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateJacChandigarh(List<JacChandigarh> JacChandigarhList) {
        try{
            JacChandigarhList.forEach(jacChandigarh -> {
                jacChandigarhRepository.save(jacChandigarh);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of JacChandigarh List has been Failed!!", false);
        }

        return new ApiResponse("Migration of JacChandigarh List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateJacDelhi(List<JacDelhi> jacDelhiList) {
        try{
            jacDelhiList.forEach(jacDelhi -> {
                jacDelhiRepository.save(jacDelhi);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of JacDelhi List has been Failed!!", false);
        }

        return new ApiResponse("Migration of JacDelhi List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateMHTCET(List<MHTCET> mhtcetList) {
        try{
            mhtcetList.forEach(mhtcet -> {
                mhtcetRepository.save(mhtcet);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of MHTCET List has been Failed!!", false);
        }

        return new ApiResponse("Migration of MHTCET List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateUPTAC(List<UPTAC> uptacList) {
        try{
            uptacList.forEach(uptac -> {
                uptacRepository.save(uptac);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of UPTAC List has been Failed!!", false);
        }

        return new ApiResponse("Migration of UPTAC List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateWBJEE(List<WBJEE> wbjeeList) {
        try{
            wbjeeList.forEach(wbjee -> {
                wbjeeRepository.save(wbjee);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of WBJEE List has been Failed!!", false);
        }

        return new ApiResponse("Migration of WBJEE List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateJosaaCsab(List<JosaaCsab> josaaCsabList) {
        try{
            josaaCsabList.forEach(josaaCsab -> {
                josaaCsabRepository.save(josaaCsab);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of Josaa Csab List has been Failed!!", false);
        }

        return new ApiResponse("Migration of Josaa Csab List has been successfully Completed!!", true);

    }

    @Override
    public ApiResponse migrateHomeState(List<HomeState> homeStateList) {
        try{
            homeStateList.forEach(homeState -> {
                homeStateRepository.save(homeState);
            });

        } catch (Exception e) {
            log.info(e.getMessage());
            return new ApiResponse("Migration of HomeState List has been Failed!!", false);
        }

        return new ApiResponse("Migration of HomeState List has been successfully Completed!!", true);

    }

    @Override
    public List<ComedK> getAllComedKData() {
        return comedKRepository.findAll();
    }
}
