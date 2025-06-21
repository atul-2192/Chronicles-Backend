package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.MHTCET;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MHTCETRepository extends JpaRepository<MHTCET,Long> {
    List<MHTCET> findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(BigDecimal cutOff);


    List<MHTCET> findByCutOffLessThanEqual(BigDecimal cutOff);

}