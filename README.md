# SMTP Connector

A robust Java-based SMTP and IMAP connector library designed for seamless email operations with modern security standards.

## Features

✅ **SMTP Email Sending** - Send emails with authentication  
✅ **IMAP Email Receiving** - Fetch emails from IMAP servers  
✅ **SSL/TLS Support** - Secure email transmission  
✅ **STARTTLS Support** - Enhanced encryption protocols  
✅ **Attachment Support** - Send and receive file attachments  
✅ **Flexible Configuration** - Easy to configure for any SMTP/IMAP provider  
✅ **Exception Handling** - Custom exceptions for better error management  
✅ **Unit Tests** - Comprehensive test coverage

## Requirements

- Java 11 or higher  
- Maven 3.6 or higher

## Installation

1. Clone the repository:  
```bash
git clone https://github.com/nsinhans/smtp-connector.git  
cd smtp-connector
```

2. Build the project:  
```bash
mvn clean install
```

## Quick Start

### Sending an Email

```java
// Create configuration
EmailConfig config = new EmailConfig();
config.setSmtpHost("smtp.gmail.com");
config.setSmtpPort(587);
config.setUsername("your-email@gmail.com");
config.setPassword("your-app-password");
config.setSslEnabled(false);
config.setStarttlsEnabled(true);

// Create connector
SmtpConnector connector = new SmtpConnector(config);

// Create email message
EmailMessage message = new EmailMessage();
message.setSender("your-email@gmail.com");
message.addRecipient("recipient@example.com");
message.addCcRecipient("cc@example.com");
message.setSubject("Test Email");
message.setBody("This is a test email with attachments");
message.addAttachment("/path/to/file.pdf");

// Send email
try {
    connector.sendEmail(message);
} catch (EmailConnectorException e) {
    System.err.println("Failed to send email: " + e.getMessage());
}
```

### Receiving Emails

```java
// Create configuration
EmailConfig config = new EmailConfig();
config.setImapHost("imap.gmail.com");
config.setImapPort(993);
config.setUsername("your-email@gmail.com");
config.setPassword("your-app-password");

// Create receiver
ImapReceiver receiver = new ImapReceiver(config);

try {
    // Connect to IMAP server
    receiver.connect();
    
    // Fetch emails from INBOX
    Message[] messages = receiver.fetchEmails("INBOX");
    
    for (Message message : messages) {
        System.out.println("From: " + message.getFrom()[0]);
        System.out.println("Subject: " + message.getSubject());
        System.out.println("Body: " + message.getContent());
        
        // Save attachments
        AttachmentHandler.saveAttachments(message, "/path/to/save/attachments");
    }
    
    // Disconnect
    receiver.disconnect();
} catch (EmailConnectorException e) {
    System.err.println("Error: " + e.getMessage());
}
```

## Configuration

### SMTP Configuration for Popular Providers

#### Gmail
```java
config.setSmtpHost("smtp.gmail.com");
config.setSmtpPort(587);
config.setStarttlsEnabled(true);
config.setSslEnabled(false);
```

#### Outlook
```java
config.setSmtpHost("smtp-mail.outlook.com");
config.setSmtpPort(587);
config.setStarttlsEnabled(true);
config.setSslEnabled(false);
```

#### Yahoo
```java
config.setSmtpHost("smtp.mail.yahoo.com");
config.setSmtpPort(465);
config.setSslEnabled(true);
config.setStarttlsEnabled(false);
```

### IMAP Configuration for Popular Providers

#### Gmail
```java
config.setImapHost("imap.gmail.com");
config.setImapPort(993);
config.setSslEnabled(true);
```

#### Outlook
```java
config.setImapHost("imap-mail.outlook.com");
config.setImapPort(993);
config.setSslEnabled(true);
```

## API Reference

### EmailConfig
Configuration class for email settings.

- `setSmtpHost(String host)` - Set SMTP server host
- `setSmtpPort(int port)` - Set SMTP server port
- `setImapHost(String host)` - Set IMAP server host
- `setImapPort(int port)` - Set IMAP server port
- `setUsername(String username)` - Set email username
- `setPassword(String password)` - Set email password
- `setSslEnabled(boolean enabled)` - Enable/disable SSL
- `setStarttlsEnabled(boolean enabled)` - Enable/disable STARTTLS

### SmtpConnector
Main class for sending emails.

- `sendEmail(EmailMessage message)` - Send an email message

### ImapReceiver
Main class for receiving emails.

- `connect()` - Connect to IMAP server
- `fetchEmails(String folderName)` - Fetch emails from folder
- `disconnect()` - Disconnect from IMAP server

### EmailMessage
Data model for email messages.

- `setSender(String sender)` - Set sender email
- `addRecipient(String recipient)` - Add TO recipient
- `addCcRecipient(String ccRecipient)` - Add CC recipient
- `addBccRecipient(String bccRecipient)` - Add BCC recipient
- `setSubject(String subject)` - Set email subject
- `setBody(String body)` - Set email body
- `addAttachment(String path)` - Add attachment
- `setHtmlBody(boolean html)` - Set HTML body format

## Error Handling

All exceptions are wrapped in `EmailConnectorException`:

```java
try {
    connector.sendEmail(message);
} catch (EmailConnectorException e) {
    System.err.println("Error: " + e.getMessage());
    e.printStackTrace();
}
```

## Running Tests

Run unit tests with Maven:

```bash
mvn test
```

## Security Considerations

1. **Never hardcode credentials** - Use environment variables or configuration files
2. **Use App Passwords** - For Gmail, use app-specific passwords, not your main password
3. **Enable 2FA** - Use two-factor authentication on your email account
4. **Use SSL/TLS** - Always encrypt email transmission
5. **Validate Attachments** - Check file types and sizes before processing

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Support

For issues and questions, please open an issue on GitHub.

## Roadmap

- [ ] Add support for POP3
- [ ] Add OAuth2 authentication
- [ ] Add email template support
- [ ] Add retry mechanism
- [ ] Add logging framework integration
- [ ] Add async email sending
