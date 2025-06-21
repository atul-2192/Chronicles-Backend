package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.UPTAC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UPTACRepository extends JpaRepository<UPTAC,Long> {
    List<UPTAC> findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(int cutOff);

}

