package com.fiap.hackathon.infrastructure.presentation.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationEventMessage {
    private String id;
    private String status;
}