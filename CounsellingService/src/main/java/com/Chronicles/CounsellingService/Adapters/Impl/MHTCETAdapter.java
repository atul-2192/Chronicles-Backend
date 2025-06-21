package com.Chronicles.CounsellingService.Adapters.Impl;


import com.Chronicles.CounsellingService.Adapters.CollegeAdapter;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.ComedK;
import com.Chronicles.CounsellingService.Entity.MHTCET;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MHTCETAdapter implements CollegeAdapter<MHTCET> {


    @Override
    public List<CollegeDTO> convert(List<MHTCET> list) {
        return list.stream()
                .map(c -> new CollegeDTO(c.getCollegeName(), c.getBranchName(), c.getCutOff()))
                .collect(Collectors.toList());
    }

    @Override
    public Class<MHTCET> getSourceType() {
        return MHTCET.class;
    }
}
