package com.Chronicles.MeetingService.Repository;

import com.Chronicles.MeetingService.Entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
