package com.yourcompany.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MeetingEventDTO {
    private String emailId;
    private String name;
    private LocalDateTime meetingAt;
    private String queryDescription;
}
