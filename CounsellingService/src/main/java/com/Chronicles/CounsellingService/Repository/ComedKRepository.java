package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.ComedK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComedKRepository extends JpaRepository<ComedK, Long> {
    List<ComedK> findTop50ByCutOffGreaterThanEqualOrderByCutOffAsc(Long cutOff);

}