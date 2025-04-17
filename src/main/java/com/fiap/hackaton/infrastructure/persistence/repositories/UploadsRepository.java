package com.fiap.hackaton.infrastructure.persistence.repositories;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import com.fiap.hackaton.core.domain.entities.Uploads;
import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UploadsRepository {
    private final DynamoDbTemplate dynamoDbTemplate;

    public Optional<Uploads> findById(String id) {
        Key key = Key.builder().partitionValue(id).build();
        Uploads uploadEntity = dynamoDbTemplate.load(key, Uploads.class);
        if (Objects.nonNull(uploadEntity)) {
            return Optional.of(uploadEntity);
        }
        return Optional.empty();
    }

    public void save(Uploads uploadEntity) {
        dynamoDbTemplate.save(uploadEntity);
    }
}