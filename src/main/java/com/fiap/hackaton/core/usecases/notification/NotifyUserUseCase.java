package com.fiap.hackaton.core.usecases.notification;

import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.core.usecases.notification.ports.EmailGateway;
import com.fiap.hackaton.infrastructure.persistence.repositories.UploadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotifyUserUseCaseImpl implements NotifyUserUseCase {
    private final UploadsRepository uploadRepository;
    private final EmailGateway emailGateway;

    @Override
    public void execute(String uploadId) {
        var upload = uploadRepository.findById(uploadId)
                .orElseThrow(() -> new NotificationException("Upload not found: " + uploadId));

        if (!upload.canBeNotified()) {
            throw new NotificationException("Upload cannot be notified: " + uploadId);
        }

        var email = upload.composeNotificationEmail();
        emailGateway.send(email);
        uploadRepository.save(upload.markAsNotified());
    }
}
