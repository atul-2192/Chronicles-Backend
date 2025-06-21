package com.Chronicles.CounsellingService.Adapters;

import com.Chronicles.CounsellingService.DTO.CollegeDTO;

import java.util.List;

public interface CollegeAdapter <T>{

    List<CollegeDTO> convert(List<T> list);
    Class<T> getSourceType();
}
