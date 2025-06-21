package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.JacChandigarh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JacChandigarhRepository extends JpaRepository<JacChandigarh, Long> {

    List<JacChandigarh> findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(int cutOff);
}

