package com.yourcompany.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentConfirmationEvent {
    private String email;
    private String productName;
    private Long price;
    private String status;
}