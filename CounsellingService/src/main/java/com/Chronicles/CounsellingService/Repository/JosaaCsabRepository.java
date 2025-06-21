package com.Chronicles.CounsellingService.Repository;

import com.Chronicles.CounsellingService.Entity.JosaaCsab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JosaaCsabRepository extends JpaRepository<JosaaCsab, Long> {

    List<JosaaCsab> findTop100ByGenJosaaGreaterThanEqualOrderByGenJosaaAsc(int cutOff);

    List<JosaaCsab> findTop100ByEwsJosaaGreaterThanEqualOrderByEwsJosaaAsc(int cutOff);

    List<JosaaCsab> findTop100ByObcJosaaGreaterThanEqualOrderByObcJosaaAsc(int cutOff);

    List<JosaaCsab> findTop100ByScJosaaGreaterThanEqualOrderByScJosaaAsc(int cutOff);

    List<JosaaCsab> findTop100ByStJosaaGreaterThanEqualOrderByStJosaaAsc(int cutOff);

    List<JosaaCsab> findTop100ByGenCsabGreaterThanEqualOrderByGenCsabAsc(int cutOff);

    List<JosaaCsab> findTop100ByEwsCsabGreaterThanEqualOrderByEwsCsabAsc(int cutOff);

    List<JosaaCsab> findTop100ByObcCsabGreaterThanEqualOrderByObcCsabAsc(int cutOff);

    List<JosaaCsab> findTop100ByScCsabGreaterThanEqualOrderByScCsabAsc(int cutOff);

    List<JosaaCsab> findTop100ByStCsabGreaterThanEqualOrderByStCsabAsc(int cutOff);

}

