package com.example.email;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.util.Properties;

/**
 * SMTP Connector for sending emails with SSL/TLS support and attachments.
 */
public class SmtpConnector {
    private EmailConfig config;

    public SmtpConnector(EmailConfig config) {
        this.config = config;
    }

    /**
     * Send an email message.
     *
     * @param message EmailMessage object containing email details
     * @throws EmailConnectorException if email sending fails
     */
    public void sendEmail(EmailMessage message) throws EmailConnectorException {
        try {
            Properties props = configureProperties();
            Session session = createSession(props);
            MimeMessage mimeMessage = createMimeMessage(session, message);
            Transport.send(mimeMessage);
            System.out.println("Email sent successfully to: " + String.join(", ", message.getRecipients()));
        } catch (MessagingException e) {
            throw new EmailConnectorException("Failed to send email: " + e.getMessage(), e);
        }
    }

    /**
     * Configure SMTP properties based on EmailConfig.
     */
    private Properties configureProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", config.getSmtpHost());
        props.put("mail.smtp.port", config.getSmtpPort());

        if (config.isSslEnabled()) {
            props.put("mail.smtp.socketFactory.port", config.getSmtpPort());
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }

        if (config.isStarttlsEnabled()) {
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.starttls.required", "true");
        }

        return props;
    }

    /**
     * Create an authenticated mail session.
     */
    private Session createSession(Properties props) {
        return Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getUsername(), config.getPassword());
            }
        });
    }

    /**
     * Create a MIME message from EmailMessage object.
     */
    private MimeMessage createMimeMessage(Session session, EmailMessage message) throws MessagingException {
        MimeMessage mimeMessage = new MimeMessage(session);
        mimeMessage.setFrom(new InternetAddress(message.getSender()));

        // Set recipients
        for (String recipient : message.getRecipients()) {
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }

        // Set CC recipients
        for (String ccRecipient : message.getCcRecipients()) {
            mimeMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccRecipient));
        }

        // Set BCC recipients
        for (String bccRecipient : message.getBccRecipients()) {
            mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress(bccRecipient));
        }

        mimeMessage.setSubject(message.getSubject());

        // Create multipart message for body and attachments
        Multipart multipart = new MimeMultipart();

        // Add body
        BodyPart bodyPart = new MimeBodyPart();
        if (message.isHtmlBody()) {
            bodyPart.setContent(message.getBody(), "text/html; charset=utf-8");
        } else {
            bodyPart.setText(message.getBody());
        }
        multipart.addBodyPart(bodyPart);

        // Add attachments
        for (String attachmentPath : message.getAttachmentPaths()) {
            File attachmentFile = new File(attachmentPath);
            if (attachmentFile.exists()) {
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(new DataHandler(new FileDataSource(attachmentFile)));
                attachmentBodyPart.setFileName(attachmentFile.getName());
                multipart.addBodyPart(attachmentBodyPart);
            }
        }

        mimeMessage.setContent(multipart);
        return mimeMessage;
    }
}