// package com.fiap.hackaton.integration;

// import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
// import com.amazonaws.services.dynamodbv2.model.AttributeValue;
// import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
// import com.fiap.hackaton.domain.enums.UploadStatus;
// import io.awspring.cloud.sqs.operations.SqsTemplate;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.testcontainers.containers.localstack.LocalStackContainer;
// import org.testcontainers.junit.jupiter.Container;
// import org.testcontainers.junit.jupiter.Testcontainers;
// import org.testcontainers.utility.DockerImageName;

// import java.util.HashMap;
// import java.util.Map;
// import java.util.UUID;

// import static org.awaitility.Awaitility.await;
// import static org.testcontainers.containers.localstack.LocalStackContainer.Service.DYNAMODB;
// import static org.testcontainers.containers.localstack.LocalStackContainer.Service.SQS;
// import static java.util.concurrent.TimeUnit.SECONDS;

// @SpringBootTest
// @Testcontainers
// class NotificationIntegrationTest {

//     @Container
//     static LocalStackContainer localStack = new LocalStackContainer(DockerImageName.parse("localstack/localstack:2.1"))
//             .withServices(DYNAMODB, SQS);

//     @Autowired
//     private SqsTemplate sqsTemplate;

//     @Autowired
//     private AmazonDynamoDB dynamoDB;

//     @DynamicPropertySource
//     static void overrideConfiguration(DynamicPropertyRegistry registry) {
//         registry.add("aws.endpoint", () -> localStack.getEndpointOverride(DYNAMODB).toString());
//         registry.add("aws.region", localStack::getRegion);
//         registry.add("aws.credentials.access-key", localStack::getAccessKey);
//         registry.add("aws.credentials.secret-key", localStack::getSecretKey);
//     }

//     @Test
//     void shouldProcessNotificationSuccessfully() {
//         // Arrange
//         var uploadId = UUID.randomUUID();
//         createUploadInDynamoDB(uploadId);

//         // Act
//         sqsTemplate.send("notification-queue", Map.of("id", uploadId.toString()));

//         // Assert
//         await()
//             .atMost(5, SECONDS)
//             .untilAsserted(() -> {
//                 // Verify the upload status was updated to NOTIFIED
//                 var item = dynamoDB.getItem("uploads", Map.of("id", new AttributeValue(uploadId.toString())));
//                 assert item.getItem().get("status").getS().equals(UploadStatus.NOTIFIED.name());
//             });
//     }

//     private void createUploadInDynamoDB(UUID id) {
//         Map<String, AttributeValue> item = new HashMap<>();
//         item.put("id", new AttributeValue(id.toString()));
//         item.put("email", new AttributeValue("test@example.com"));
//         item.put("status", new AttributeValue(UploadStatus.PROCESSED.name()));
//         item.put("urlDownload", new AttributeValue("https://example.com/download"));
//         item.put("createdAt", new AttributeValue().withN(String.valueOf(System.currentTimeMillis())));

//         dynamoDB.putItem(new PutItemRequest("uploads", item));
//     }
// }