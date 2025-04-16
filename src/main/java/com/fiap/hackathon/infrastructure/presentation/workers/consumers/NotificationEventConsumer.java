package com.fiap.hackathon.infrastructure.presentation.workers.consumers;

import com.fiap.hackathon.application.usecases.NotifyUserUseCase;
import com.fiap.hackathon.infrastructure.presentation.workers.dto.NotificationEventMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventConsumer {
    private final NotifyUserUseCase notifyUserUseCase;

    @SqsListener("${app.message-broker.event.notification.queue-name}")
    public void listen(@Payload NotificationEventMessage message) {
        log.info("Notification event message received: {}", message);
        notifyUserUseCase.execute(message.getId(), message.getStatus());
    }
}
