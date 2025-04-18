package com.fiap.hackathon.infrastructure.gateways;

import com.fiap.hackathon.fixture.FixtureTest;
import com.fiap.hackaton.core.domain.entities.Email;
import com.fiap.hackaton.core.domain.exceptions.NotificationException;
import com.fiap.hackaton.infrastructure.gateways.SendGridEmailGateway;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendGridEmailGatewayTest extends FixtureTest {

    @Mock
    private SendGrid mockSendGrid;
    private SendGridEmailGateway emailGateway;

    private final String fromEmail = "test@example.com";

    @BeforeEach
    void setUp() {
        emailGateway = new SendGridEmailGateway(fromEmail, mockSendGrid);
    }

    @Test
    void testSendEmailSuccessfully() throws Exception {
        // Arrange
        Email email = new Email("recipient@example.com", "Test Subject", "Test Body");
        Response mockResponse = new Response();
        mockResponse.setStatusCode(200);
        when(mockSendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act
        emailGateway.send(email);

        // Assert
        ArgumentCaptor<Request> requestCaptor = ArgumentCaptor.forClass(Request.class);
        verify(mockSendGrid).api(requestCaptor.capture());
        Request capturedRequest = requestCaptor.getValue();
        assertNotNull(capturedRequest);
        assertEquals("mail/send", capturedRequest.getEndpoint());
    }

    @Test
    void testSendEmailFailsWithErrorResponse() throws Exception {
        // Arrange
        Email email = new Email("recipient@example.com", "Test Subject", "Test Body");
        Response mockResponse = new Response();
        mockResponse.setStatusCode(400);
        mockResponse.setBody("Error sending email");
        when(mockSendGrid.api(any(Request.class))).thenReturn(mockResponse);

        // Act & Assert
        NotificationException exception = assertThrows(NotificationException.class, () -> emailGateway.send(email));
        assertTrue(exception.getMessage().contains("Error sending email"));
    }

    @Test
    void testSendEmailThrowsException() throws Exception {
        // Arrange
        Email email = new Email("recipient@example.com", "Test Subject", "Test Body");
        when(mockSendGrid.api(any(Request.class))).thenThrow(new RuntimeException("Unexpected error"));

        // Act & Assert
        NotificationException exception = assertThrows(NotificationException.class, () -> emailGateway.send(email));
        assertTrue(exception.getMessage().contains("Error sending email"));
    }
}