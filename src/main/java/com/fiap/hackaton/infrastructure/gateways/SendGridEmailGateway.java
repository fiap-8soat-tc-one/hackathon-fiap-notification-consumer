package com.fiap.hackaton.infrastructure.gateways;

import com.fiap.hackaton.core.domain.entities.Email;
import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.core.usecases.notification.ports.EmailGatewaySpec;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SendGridEmailGateway implements EmailGatewaySpec {
    private final SendGrid sendGrid;
    private final String fromEmail;

    public SendGridEmailGateway(
        @Value("${sendgrid.email.from}") String fromEmail,
        SendGrid sendGrid
    ) {
        this.sendGrid = sendGrid;
        this.fromEmail = fromEmail;
    }

    @Override
    public void send(Email email) {
        try {
            var from = new com.sendgrid.helpers.mail.objects.Email(fromEmail);
            var to = new com.sendgrid.helpers.mail.objects.Email(email.to());
            var content = new Content("text/plain", email.body());
            var mail = new Mail(from, email.subject(), to, content);

            var request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            var response = sendGrid.api(request);
            if (response.getStatusCode() >= 400) {
                throw new NotificationException("Failed to send email: " + response.getBody());
            }
        } catch (Exception e) {
            throw new NotificationException("Error sending email", e);
        }
    }
}