package com.Chronicles.EntityService.Repository;

import com.Chronicles.EntityService.Entity.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate,String> {
}
