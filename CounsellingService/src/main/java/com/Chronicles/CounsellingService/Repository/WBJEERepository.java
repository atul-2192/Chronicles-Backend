package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.WBJEE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WBJEERepository extends JpaRepository<WBJEE,Long> {
    List<WBJEE> findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(int cutOff);

}

