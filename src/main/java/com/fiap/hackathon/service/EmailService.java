package com.fiap.hackathon.service;

import com.fiap.hackathon.infrastructure.model.EmailNotification;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final SendGrid sendGrid;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void sendEmail(EmailNotification notification) {
        Email from = new Email(fromEmail);
        Email to = new Email(notification.getTo());

        Content content = new Content("text/plain", notification.getBody());
        Mail mail = new Mail(from, notification.getSubject(), to, content);

        System.out.println("Sending email with from: " + fromEmail);
        System.out.println("Sending email with body: " + notification.getBody());
        System.out.println("Sending email with subject: " + notification.getSubject());
        System.out.println("Sending email to: " + notification.getTo());
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sendGrid.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage(), e);
        }
    }
}
