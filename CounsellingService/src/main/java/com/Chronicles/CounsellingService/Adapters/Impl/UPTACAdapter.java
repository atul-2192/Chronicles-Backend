package com.Chronicles.CounsellingService.Adapters.Impl;


import com.Chronicles.CounsellingService.Adapters.CollegeAdapter;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.ComedK;
import com.Chronicles.CounsellingService.Entity.UPTAC;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UPTACAdapter implements CollegeAdapter<UPTAC> {


    @Override
    public List<CollegeDTO> convert(List<UPTAC> list) {
        return list.stream()
                .map(c -> new CollegeDTO(c.getCollegeName(), c.getBranchName(), BigDecimal.valueOf(c.getCutOff())))
                .collect(Collectors.toList());
    }

    @Override
    public Class<UPTAC> getSourceType() {
        return UPTAC.class;
    }
}
