package com.fiap.hackathon.infrastructure.gateways;

import com.fiap.hackathon.service.NotificationService;
import com.fiap.hackathon.application.gateways.NotifyUserSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyUserGateway implements NotifyUserSpec {
    private final NotificationService notificationService;

    @Override
    public void execute(String id, String status) {
        System.out.println("Notifying id " + id + " with status: " + status);
        notificationService.notifyUser(id, status);
    }
}
