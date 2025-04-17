package com.fiap.hackaton.core.usecases.notification;

import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.core.usecases.notification.ports.EmailGatewaySpec;
import com.fiap.hackaton.domain.enums.UploadStatus;
import com.fiap.hackaton.infrastructure.persistence.repositories.UploadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotifyUserUseCase implements NotifyUserUseCaseSpec {
    private final UploadsRepository uploadRepository;
    private final EmailGatewaySpec emailGateway;

    @Override
    public void execute(String uploadId) {
        var upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new NotificationException("Upload not found: " + uploadId));

        var email = upload.composeNotificationEmail();
        emailGateway.send(email);

        upload.setStatus(UploadStatus.NOTIFIED.name());
        uploadRepository.save(upload);
    }
}
