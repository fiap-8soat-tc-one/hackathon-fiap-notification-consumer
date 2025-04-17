package com.fiap.hackaton.core.domain.entities;

import com.fiap.hackaton.domain.enums.UploadStatus;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Setter
@DynamoDbBean
@ToString
public class Uploads {
    private UUID id;

    private String email;

    private String status;

    private String urlDownload;

    private Instant createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public UUID getId() {
        return id;
    }

    @DynamoDbAttribute("email")
    public String getEmail() {
        return email;
    }

    @DynamoDbAttribute("status_upload")
    public String getStatus() {
        return status;
    }

    @DynamoDbAttribute("url_download")
    public String getUrlDownload() {
        return urlDownload;
    }

    @DynamoDbAttribute("data_criacao")
    public Instant getCreatedAt() {
        return createdAt;
    }

    public Email composeNotificationEmail() {
        var body = new StringBuilder()
                .append("Olá,\n\n")
                .append("Seu video ").append(id).append(" foi processado com o status ").append(status).append("\n\n");

        if (Objects.equals(status, UploadStatus.PROCESSED.name())) {
            body.append("Use o link a seguir para realizar o download do arquivo zip contendo os screenshots:\n")
                    .append(urlDownload).append("\n\n");
        } else {
            body.append("Ocorreu um erro durante o processamento. Por favor, contate o time de suporte.\n\n");
        }

        body.append("Obrigado por utilizar nosso servico!\n");

        return new Email(email, "Processamento de video concluido", body.toString());
    }
}
