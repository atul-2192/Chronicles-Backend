package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.HomeState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeStateRepository extends JpaRepository<HomeState,Long> {
    List<HomeState> findByCutOffGreaterThanEqual(int cutOff);
}