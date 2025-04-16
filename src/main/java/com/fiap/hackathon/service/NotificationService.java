package com.fiap.hackathon.service;

import com.fiap.hackathon.domain.enums.UploadStatus;
import com.fiap.hackathon.infrastructure.model.EmailNotification;
import com.fiap.hackathon.infrastructure.persistence.entities.Uploads;
import com.fiap.hackathon.infrastructure.persistence.repositories.UploadsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final EmailService emailService;
    private final UploadsRepository uploadsRepository;

    public void notifyUser(String id, String status) {
        log.info("Notifying user with id {} and status {}", id, status);
        Uploads uploadEntity = uploadsRepository.findById(id).orElseThrow(() -> new RuntimeException("Upload not found"));
        if(!uploadEntity.getStatusUpload()
                .equals(UploadStatus.PROCESSED.name())){
            throw new RuntimeException("Only processed uploads can be notified");
        }
        EmailNotification emailNotification = new EmailNotification();
        emailNotification.setTo(uploadEntity.getEmail());
        emailNotification.setBody(buildEmailContent(uploadEntity));
        emailNotification.setSubject("Processamento de video concluido");
        emailService.sendEmail(emailNotification);
        uploadEntity.setStatusUpload(UploadStatus.NOTIFIED.name());
        uploadsRepository.save(uploadEntity);
    }

    private String buildEmailContent(Uploads uploadEntity) {
        StringBuilder result = new StringBuilder("Olá,\n\n" +
                "Seu video " + uploadEntity.getId() + " foi processado com o status " + uploadEntity.getStatusUpload() + "\n\n");

        if (uploadEntity.getStatusUpload()            .equals(UploadStatus.PROCESSED.name())) {
            result.append("Use o link a seguir para realizar o download do arquivo zip contendo os screenshots:\n");
            result.append(uploadEntity.getUrlDownload()).append("\n\n");
        } else {
            result.append("Ocorreu um erro durante o processamento. Por favor, contate o time de suporte.\n\n");
        }
        result.append("Obrigado por utilizar nosso servico!\n");
        return result.toString();
    }
}
