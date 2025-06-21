package com.Chronicles.CounsellingService.Adapters.Impl;


import com.Chronicles.CounsellingService.Adapters.CollegeAdapter;
import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import com.Chronicles.CounsellingService.Entity.ComedK;
import com.Chronicles.CounsellingService.Entity.JacDelhi;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JacDelhiAdapter implements CollegeAdapter<JacDelhi> {


    @Override
    public List<CollegeDTO> convert(List<JacDelhi> list) {
        return list.stream()
                .map(c -> new CollegeDTO(c.getCollegeName(), c.getBranchName(), BigDecimal.valueOf(0)))
                .collect(Collectors.toList());
    }

    @Override
    public Class<JacDelhi> getSourceType() {
        return JacDelhi.class;
    }
}
