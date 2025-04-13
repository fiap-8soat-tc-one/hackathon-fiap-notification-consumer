package com.fiap.hackathon.service;

import com.fiap.hackathon.model.EmailNotification;
import com.sendgrid.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final SendGrid sendGrid;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public EmailService(SendGrid sendGrid) {
        this.sendGrid = sendGrid;
    }

    public void sendEmail(EmailNotification notification) {
//        Email from = new Email(fromEmail);
//        Email to = new Email(notification.getTo());
//        Content content = new Content("text/plain", notification.getBody());
//        Mail mail = new Mail(from, notification.getSubject(), to, content);
//
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            sendGrid.api(request);
//        } catch (Exception e) {
//            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
//        }
    }
}
