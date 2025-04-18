package com.fiap.hackaton.infrastructure.messaging.consumers;

import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.infrastructure.presentation.workers.consumers.SQSNotificationConsumer;
import com.fiap.hackaton.infrastructure.presentation.workers.dto.NotificationEventMessage;
import com.fiap.hackaton.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class SQSNotificationConsumerTest {

    @Mock
    private NotificationService notificationService;

    @Mock
    private Logger log;

    @InjectMocks
    private SQSNotificationConsumer sqsNotificationConsumer;

    public SQSNotificationConsumerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListen_Success() {
        // Arrange
        doNothing().when(notificationService).processNotification(any());
        NotificationEventMessage message = NotificationEventMessage.builder()
                .id("test-id")
                .status("PROCESSED")
                .build();

        // Act
        sqsNotificationConsumer.listen(message);
    }

    @Test
    void testListen_NotificationException() {
        // Arrange
        NotificationEventMessage message = NotificationEventMessage.builder()
                .id("test-id")
                .status("PROCESSED")
                .build();
        doThrow(new NotificationException("Error")).when(notificationService).processNotification(message);

        // Act and Assert
        assertDoesNotThrow(() -> sqsNotificationConsumer.listen(message));
    }
}