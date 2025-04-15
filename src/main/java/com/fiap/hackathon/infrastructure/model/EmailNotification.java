package com.fiap.hackathon.infrastructure.model;

import lombok.Data;

@Data
public class EmailNotification {
    private String to;
    private String subject;
    private String body;
}
