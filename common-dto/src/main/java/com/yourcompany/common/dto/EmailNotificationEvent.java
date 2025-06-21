package com.yourcompany.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailNotificationEvent {
    private String emailId;
    private String pdfPath;

    private String name;
    private String roomId;
    private String queryDescription;
    private LocalDateTime meetingAt;
}

