package com.Chronicles.CounsellingService.Service;
import com.Chronicles.CounsellingService.Entity.*;
import com.Chronicles.CounsellingService.DTO.ApiResponse;

import java.util.List;

public interface DataMigrationService {

    ApiResponse migrateComedK(List<ComedK> comedKList);
    ApiResponse migrateGGSIPU(List<GGSIPU> GGSIPUList);
    ApiResponse migrateJacChandigarh(List<JacChandigarh> JacChandigarhList);
    ApiResponse migrateJacDelhi(List<JacDelhi> jacDelhiList);
    ApiResponse migrateMHTCET(List<MHTCET> mhtcetList);
    ApiResponse migrateUPTAC(List<UPTAC> uptacList);
    ApiResponse migrateWBJEE(List<WBJEE> wbjeeList);
    ApiResponse migrateJosaaCsab(List<JosaaCsab> josaaCsabList);
    ApiResponse migrateHomeState(List<HomeState> homeStateList);

    List<ComedK> getAllComedKData();

}

