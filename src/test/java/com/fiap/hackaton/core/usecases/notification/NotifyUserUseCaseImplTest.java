// package com.fiap.hackaton.core.usecases.notification;

// import com.fiap.hackaton.core.domain.entities.Email;
// import com.fiap.hackaton.core.domain.entities.Upload;
// import com.fiap.hackaton.core.domain.exceptions.NotificationException;
// import com.fiap.hackaton.core.usecases.notification.ports.EmailGateway;
// import com.fiap.hackaton.core.usecases.notification.ports.UploadRepository;
// import com.fiap.hackaton.domain.enums.UploadStatus;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.time.Instant;
// import java.util.Optional;
// import java.util.UUID;

// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.*;

// @ExtendWith(MockitoExtension.class)
// class NotifyUserUseCaseImplTest {

//     @Mock
//     private UploadRepository uploadRepository;

//     @Mock
//     private EmailGateway emailGateway;

//     private NotifyUserUseCase notifyUserUseCase;

//     @BeforeEach
//     void setUp() {
//         notifyUserUseCase = new NotifyUserUseCaseImpl(uploadRepository, emailGateway);
//     }

//     @Test
//     void shouldNotifyUserSuccessfully() {
//         // Arrange
//         var uploadId = UUID.randomUUID();
//         var upload = new Upload(
//             uploadId,
//             "test@example.com",
//             UploadStatus.PROCESSED,
//             "https://example.com/download",
//             Instant.now()
//         );

//         when(uploadRepository.findById(uploadId)).thenReturn(Optional.of(upload));

//         // Act
//         notifyUserUseCase.execute(uploadId);

//         // Assert
//         verify(emailGateway).send(any(Email.class));
//         verify(uploadRepository).save(any(Upload.class));
//     }

//     @Test
//     void shouldThrowExceptionWhenUploadNotFound() {
//         // Arrange
//         var uploadId = UUID.randomUUID();
//         when(uploadRepository.findById(uploadId)).thenReturn(Optional.empty());

//         // Act & Assert
//         assertThrows(NotificationException.class, () -> notifyUserUseCase.execute(uploadId));
//         verify(emailGateway, never()).send(any());
//         verify(uploadRepository, never()).save(any());
//     }

//     @Test
//     void shouldThrowExceptionWhenUploadNotProcessed() {
//         // Arrange
//         var uploadId = UUID.randomUUID();
//         var upload = new Upload(
//             uploadId,
//             "test@example.com",
//             UploadStatus.PENDING,
//             "https://example.com/download",
//             Instant.now()
//         );

//         when(uploadRepository.findById(uploadId)).thenReturn(Optional.of(upload));

//         // Act & Assert
//         assertThrows(NotificationException.class, () -> notifyUserUseCase.execute(uploadId));
//         verify(emailGateway, never()).send(any());
//         verify(uploadRepository, never()).save(any());
//     }
// }