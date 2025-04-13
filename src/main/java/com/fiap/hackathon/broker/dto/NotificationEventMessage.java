package com.fiap.hackathon.broker.dto;

import lombok.Data;

@Data
public class NotificationEventMessage {
    private String id;
    private String status;
}