package com.fiap.hackaton.infrastructure.services;

import com.fiap.hackaton.fixture.FixtureTest;
import com.fiap.hackaton.core.usecases.notification.NotifyUserUseCaseSpec;
import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.infrastructure.presentation.workers.dto.NotificationEventMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest extends FixtureTest {

    @Mock
    private NotifyUserUseCaseSpec mockNotifyUserUseCase;

    private NotificationService notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationService(mockNotifyUserUseCase);
    }

    @Test
    void testProcessNotificationSuccessfully() {
        // Arrange
        final String messageId = "123";
        NotificationEventMessage message = new NotificationEventMessage();
        message.setId(messageId);

        // Act
        notificationService.processNotification(message);

        // Assert
        verify(mockNotifyUserUseCase).execute(messageId);
    }

    @Test
    void testProcessNotificationThrowsException() {
        // Arrange
        final String messageId = "123";
        NotificationEventMessage message = new NotificationEventMessage();
        message.setId(messageId);
        doThrow(new NotificationException("Error processing notification"))
                .when(mockNotifyUserUseCase).execute(messageId);

        // Act & Assert
        assertThrows(NotificationException.class, () -> notificationService.processNotification(message));
        verify(mockNotifyUserUseCase).execute(messageId);
    }
}