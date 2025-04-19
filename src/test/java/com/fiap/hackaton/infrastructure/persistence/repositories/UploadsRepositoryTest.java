package com.fiap.hackaton.infrastructure.persistence.repositories;

import com.fiap.hackaton.core.domain.entities.Uploads;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import software.amazon.awssdk.enhanced.dynamodb.Key;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UploadsRepositoryTest {

    @Mock
    private DynamoDbTemplate dynamoDbTemplate;

    private UploadsRepository uploadsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        uploadsRepository = new UploadsRepository(dynamoDbTemplate);
    }

    @Nested
    @DisplayName("findById")
    class FindById {

        @Test
        @DisplayName("Returns upload when ID exists")
        void returnsUploadWhenIdExists() {
            String id = "existing-id";
            Uploads upload = new Uploads();
            Key key = Key.builder().partitionValue(id).build();
            when(dynamoDbTemplate.load(key, Uploads.class)).thenReturn(upload);

            Optional<Uploads> result = uploadsRepository.findById(id);

            assertTrue(result.isPresent());
            assertEquals(upload, result.get());
        }

        @Test
        @DisplayName("Returns empty when ID does not exist")
        void returnsEmptyWhenIdDoesNotExist() {
            String id = "non-existing-id";
            Key key = Key.builder().partitionValue(id).build();
            when(dynamoDbTemplate.load(key, Uploads.class)).thenReturn(null);

            Optional<Uploads> result = uploadsRepository.findById(id);

            assertFalse(result.isPresent());
        }
    }

    @Nested
    @DisplayName("save")
    class Save {

        @Test
        @DisplayName("Saves upload successfully")
        void savesUploadSuccessfully() {
            Uploads upload = new Uploads();

            uploadsRepository.save(upload);

            verify(dynamoDbTemplate).save(upload);
        }
    }
}