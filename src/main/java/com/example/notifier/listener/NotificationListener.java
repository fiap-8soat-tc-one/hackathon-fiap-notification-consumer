package com.example.notifier.listener;

import com.example.notifier.model.EmailNotification;
import com.example.notifier.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class NotificationListener {

    private final SqsClient sqsClient;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;
    private final String queueUrl;

    public NotificationListener(SqsClient sqsClient,
                                EmailService emailService,
                                ObjectMapper objectMapper,
                                @Value("${aws.sqs.queue-url}") String queueUrl) {
        this.sqsClient = sqsClient;
        this.emailService = emailService;
        this.objectMapper = objectMapper;
        this.queueUrl = queueUrl;
    }

    @PostConstruct
    public void startListening() {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(this::pollQueue, 0, 5, TimeUnit.SECONDS);
    }

    private void pollQueue() {
        ReceiveMessageResponse response = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(10)
                .build());

        List<Message> messages = response.messages();
        for (Message message : messages) {
            try {
                EmailNotification notification = objectMapper.readValue(message.body(), EmailNotification.class);
                emailService.sendEmail(notification);
                sqsClient.deleteMessage(DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(message.receiptHandle())
                        .build());
            } catch (Exception e) {
                System.err.println("Erro ao processar mensagem: " + e.getMessage());
            }
        }
    }
}
