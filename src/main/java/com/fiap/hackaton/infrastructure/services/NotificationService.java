package com.fiap.hackaton.infrastructure.services;

import com.fiap.hackaton.core.usecases.notification.NotifyUserUseCaseSpec;
import com.fiap.hackaton.infrastructure.presentation.workers.dto.NotificationEventMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotifyUserUseCaseSpec notifyUserUseCase;

    public void processNotification(NotificationEventMessage message) {
        log.info("Starting notification process for upload: {}", message.getId());
        notifyUserUseCase.execute(message.getId());
        log.info("Successfully completed notification process for upload: {}", message.getId());
    }
}
