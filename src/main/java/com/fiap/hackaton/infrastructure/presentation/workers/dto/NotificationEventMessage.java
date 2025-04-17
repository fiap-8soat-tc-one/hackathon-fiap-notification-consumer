package com.fiap.hackaton.infraestructure.presentation.workers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class NotificationEventMessage {
    private String id;
    private String status;
    private String message;
}