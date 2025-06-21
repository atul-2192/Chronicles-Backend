package com.Chronicles.CounsellingService.Adapters;

import com.Chronicles.CounsellingService.DTO.CollegeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class AdapterRegistry {

    private final Map<Class<?>, CollegeAdapter<?>> adapterMap;

    @Autowired
    public AdapterRegistry(List<CollegeAdapter<?>> adapters) {
        this.adapterMap = adapters.stream()
                .collect(Collectors.toMap(CollegeAdapter::getSourceType, Function.identity()));
    }

    @SuppressWarnings("unchecked")
    public <T> List<CollegeDTO> convert(List<T> sourceList) {
        if (sourceList.isEmpty()) return Collections.emptyList();
        Class<T> clazz = (Class<T>) sourceList.get(0).getClass();

        CollegeAdapter<T> adapter = (CollegeAdapter<T>) adapterMap.get(clazz);
        if (adapter == null) {
            throw new IllegalArgumentException("No adapter found for class: " + clazz.getSimpleName());
        }
        return adapter.convert(sourceList);
    }
}
