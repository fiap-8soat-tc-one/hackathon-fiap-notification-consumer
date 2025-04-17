package com.fiap.hackaton.infrastructure.messaging.consumers;

import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.infrastructure.presentation.workers.dto.NotificationEventMessage;
import com.fiap.hackaton.service.NotificationService;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
@Component
@RequiredArgsConstructor
@Slf4j
public class SQSNotificationConsumer {
    private final NotificationService notificationService;

    @SqsListener("${app.message-broker.event.notification.queue-name}")
    public void listen(@Payload NotificationEventMessage message) {
        try {
            log.info("Received message from SQS: {}", message);
            notificationService.processNotification(message);
        } catch (NotificationException e) {
            log.error("Error processing message from SQS: {}", message, e);
        }
    }
}