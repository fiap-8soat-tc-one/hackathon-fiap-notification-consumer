package com.fiap.hackaton.core.domain.entities;

import com.fiap.hackaton.core.domain.exceptions.NotificationException;

public record Email(String to, String subject, String body) {
    public Email {
        if (to == null || to.isBlank()) {
            throw new NotificationException("Email recipient cannot be empty");
        }
        if (!to.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new NotificationException("Invalid email format");
        }
        if (subject == null || subject.isBlank()) {
            throw new NotificationException("Email subject cannot be empty");
        }
        if (body == null || body.isBlank()) {
            throw new NotificationException("Email body cannot be empty");
        }
    }
}