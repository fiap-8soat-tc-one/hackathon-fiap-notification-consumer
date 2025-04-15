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
        emailNotification.setSubject("File Processed Successfully");
        emailService.sendEmail(emailNotification);
        uploadEntity.setStatusUpload(UploadStatus.NOTIFIED.name());
        uploadsRepository.save(uploadEntity);
    }

    private String buildEmailContent(Uploads uploadEntity) {
        return "Hello,\n\n" +
                "Your upload " + uploadEntity.getId() + " has been processed successfully" + "\n\n" +
                "Use the following link to download the zip file containing the screenshots:\n" +
                uploadEntity.getUrlDownload() + "\n\n" +
                "Thank you for using our service!\n";
    }
}
