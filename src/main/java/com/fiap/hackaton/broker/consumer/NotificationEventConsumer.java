package com.fiap.hackaton.broker.consumer;

import com.fiap.hackaton.broker.dto.NotificationEventMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationEventConsumer {

    @SqsListener("${app.message-broker.event.notification.queue-name}")
    public void listen(@Payload NotificationEventMessage message) {
        log.info("Notification message received: {}", message);
    }
}
