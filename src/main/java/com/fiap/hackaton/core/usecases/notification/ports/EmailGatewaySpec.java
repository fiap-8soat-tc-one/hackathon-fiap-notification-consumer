package com.fiap.hackaton.core.usecases.notification.ports;

import com.fiap.hackaton.core.domain.entities.Email;

public interface EmailGatewaySpec {
    void send(Email email);
}