package com.fiap.hackathon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NotificationConsumerApp {
    public static void main(String[] args) {
        SpringApplication.run(NotificationConsumerApp.class, args);
    }
}
