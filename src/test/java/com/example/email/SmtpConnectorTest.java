package com.example.email;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SMTP Connector functionality.
 */
class SmtpConnectorTest {
    private EmailConfig config;
    private SmtpConnector connector;
    private EmailMessage emailMessage;

    @BeforeEach
    void setUp() {
        config = new EmailConfig();
        config.setSmtpHost("smtp.gmail.com");
        config.setSmtpPort(587);
        config.setUsername("test@gmail.com");
        config.setPassword("testpassword");
        config.setSslEnabled(false);
        config.setStarttlsEnabled(true);

        connector = new SmtpConnector(config);

        emailMessage = new EmailMessage();
        emailMessage.setSender("test@gmail.com");
        emailMessage.addRecipient("recipient@example.com");
        emailMessage.setSubject("Test Email");
        emailMessage.setBody("This is a test email");
    }

    @Test
    void testEmailConfiguration() {
        assertNotNull(config);
        assertEquals("smtp.gmail.com", config.getSmtpHost());
        assertEquals(587, config.getSmtpPort());
        assertEquals("test@gmail.com", config.getUsername());
        assertTrue(config.isStarttlsEnabled());
    }

    @Test
    void testEmailMessageCreation() {
        assertNotNull(emailMessage);
        assertEquals("test@gmail.com", emailMessage.getSender());
        assertEquals("Test Email", emailMessage.getSubject());
        assertTrue(emailMessage.getRecipients().contains("recipient@example.com"));
    }

    @Test
    void testAddRecipient() {
        emailMessage.addRecipient("another@example.com");
        assertEquals(2, emailMessage.getRecipients().size());
        assertTrue(emailMessage.getRecipients().contains("another@example.com"));
    }

    @Test
    void testAddAttachment() {
        emailMessage.addAttachment("/path/to/file.pdf");
        assertEquals(1, emailMessage.getAttachmentPaths().size());
        assertTrue(emailMessage.getAttachmentPaths().contains("/path/to/file.pdf"));
    }

    @Test
    void testHtmlBody() {
        emailMessage.setHtmlBody(true);
        assertTrue(emailMessage.isHtmlBody());
    }

    @Test
    void testSmtpConnectorInitialization() {
        assertNotNull(connector);
        assertNotNull(config);
    }
}