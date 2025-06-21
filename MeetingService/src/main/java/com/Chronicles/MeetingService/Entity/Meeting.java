package com.Chronicles.MeetingService.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "meetings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Meeting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String emailId;
    private String name;
    private String roomId;

    private LocalDateTime createdAt;
    private LocalDateTime meetingAt;
    private String queryDescription;
    private boolean attended = false;

}

