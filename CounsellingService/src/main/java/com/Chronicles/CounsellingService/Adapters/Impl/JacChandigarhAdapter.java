package com.Chronicles.CounsellingService.Adapters.Impl;


import com.Chronicles.CounsellingService.Adapters.CollegeAdapter;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.ComedK;
import com.Chronicles.CounsellingService.Entity.JacChandigarh;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JacChandigarhAdapter implements CollegeAdapter<JacChandigarh> {


    @Override
    public List<CollegeDTO> convert(List<JacChandigarh> list) {
        return list.stream()
                .map(c -> new CollegeDTO(c.getCollegeName(), c.getBranchName(), BigDecimal.valueOf(c.getCutOff())))
                .collect(Collectors.toList());
    }

    @Override
    public Class<JacChandigarh> getSourceType() {
        return JacChandigarh.class;
    }
}
