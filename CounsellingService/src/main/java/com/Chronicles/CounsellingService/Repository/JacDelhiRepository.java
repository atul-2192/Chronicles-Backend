package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.JacDelhi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JacDelhiRepository extends JpaRepository<JacDelhi, Long> {
}

