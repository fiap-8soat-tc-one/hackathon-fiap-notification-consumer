package com.fiap.hackathon.core.usecases.notification;

import com.fiap.hackathon.fixture.FixtureTest;
import com.fiap.hackaton.core.domain.entities.Email;
import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.core.usecases.notification.NotifyUserUseCase;
import com.fiap.hackaton.core.usecases.notification.ports.EmailGatewaySpec;
import com.fiap.hackaton.domain.enums.UploadStatus;
import com.fiap.hackaton.infrastructure.persistence.repositories.UploadsRepository;
import com.fiap.hackaton.core.domain.entities.Uploads;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotifyUserUseCaseTest extends FixtureTest {

    @Mock
    private UploadsRepository mockUploadsRepository;

    @Mock
    private EmailGatewaySpec mockEmailGateway;

    private NotifyUserUseCase notifyUserUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        notifyUserUseCase = new NotifyUserUseCase(mockUploadsRepository, mockEmailGateway);
    }

    @Test
    void testExecuteSuccessfully() {
        // Arrange
        String uploadId = "123";
        Uploads mockUploads = mock(Uploads.class);
        Email mockEmail = new Email("user@example.com", "Notification", "Your upload is complete.");
        when(mockUploadsRepository.findById(uploadId)).thenReturn(Optional.of(mockUploads));
        when(mockUploads.composeNotificationEmail()).thenReturn(mockEmail);

        // Act
        notifyUserUseCase.execute(uploadId);

        // Assert
        verify(mockEmailGateway).send(mockEmail);
        verify(mockUploads).setStatus(UploadStatus.NOTIFIED.name());
        verify(mockUploadsRepository).save(mockUploads);
    }

    @Test
    void testExecuteThrowsExceptionWhenUploadNotFound() {
        // Arrange
        String uploadId = "123";
        when(mockUploadsRepository.findById(uploadId)).thenReturn(Optional.empty());

        // Act & Assert
        NotificationException exception = assertThrows(NotificationException.class, () -> notifyUserUseCase.execute(uploadId));
        assertEquals("Upload not found: " + uploadId, exception.getMessage());
        verifyNoInteractions(mockEmailGateway);
    }

    @Test
    void testExecuteThrowsExceptionWhenEmailSendingFails() {
        // Arrange
        String uploadId = "123";
        Uploads mockUploads = mock(Uploads.class);
        Email mockEmail = new Email("user@example.com", "Notification", "Your upload is complete.");
        when(mockUploadsRepository.findById(uploadId)).thenReturn(Optional.of(mockUploads));
        when(mockUploads.composeNotificationEmail()).thenReturn(mockEmail);
        doThrow(new NotificationException("Failed to send email")).when(mockEmailGateway).send(mockEmail);

        // Act & Assert
        NotificationException exception = assertThrows(NotificationException.class, () -> notifyUserUseCase.execute(uploadId));
        assertEquals("Failed to send email", exception.getMessage());
        verify(mockUploadsRepository, never()).save(mockUploads);
    }
}