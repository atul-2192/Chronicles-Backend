package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.GGSIPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GGSIPURepository extends JpaRepository<GGSIPU,Long> {

    List<GGSIPU> findByCutOffDelhiGreaterThanEqual(Long cutOff);
    List<GGSIPU> findTop50ByCutOffOutSideDelhiGreaterThanEqualOrderByCutOffOutSideDelhiAsc(Long cutOff);
}

