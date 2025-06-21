package com.Chronicles.CounsellingService.Adapters.Impl;

import com.Chronicles.CounsellingService.Adapters.CollegeAdapter;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.GGSIPU;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GGSIPUAdapter implements CollegeAdapter<GGSIPU> {

    @Override
    public List<CollegeDTO> convert(List<GGSIPU> list) {
        return list.stream()
                .map(c -> new CollegeDTO(c.getCollegeName(), c.getBranchName(), BigDecimal.valueOf(c.getCutOffOutSideDelhi())))
                .collect(Collectors.toList());
    }

    @Override
    public Class<GGSIPU> getSourceType() {
        return GGSIPU.class;
    }
}
