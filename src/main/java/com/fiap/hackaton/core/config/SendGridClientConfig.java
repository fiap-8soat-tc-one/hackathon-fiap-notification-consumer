package com.fiap.hackaton.core.config;

import com.sendgrid.SendGrid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SendGridClientConfig {
    private final String apiKey;

    public SendGridClientConfig(@Value("${sendgrid.api-key}") String apiKey) {
        this.apiKey = apiKey;
    }

    @Bean
    public SendGrid sendGrid() {
        return new SendGrid(this.apiKey);
    }
}
