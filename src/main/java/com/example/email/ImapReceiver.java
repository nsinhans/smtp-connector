package com.example.email;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

/**
 * IMAP Receiver for receiving and fetching emails with SSL/TLS support.
 */
public class ImapReceiver {
    private EmailConfig config;
    private Store store;

    public ImapReceiver(EmailConfig config) {
        this.config = config;
    }

    /**
     * Connect to the IMAP server.
     *
     * @throws EmailConnectorException if connection fails
     */
    public void connect() throws EmailConnectorException {
        try {
            Properties props = configureProperties();
            Session session = Session.getInstance(props);
            store = session.getStore("imap");
            store.connect(config.getImapHost(), config.getUsername(), config.getPassword());
            System.out.println("Connected to IMAP server successfully");
        } catch (MessagingException e) {
            throw new EmailConnectorException("Failed to connect to IMAP server: " + e.getMessage(), e);
        }
    }

    /**
     * Configure IMAP properties based on EmailConfig.
     */
    private Properties configureProperties() {
        Properties props = new Properties();
        props.put("mail.imap.host", config.getImapHost());
        props.put("mail.imap.port", config.getImapPort());
        props.put("mail.imap.socketFactory.port", config.getImapPort());

        if (config.isSslEnabled()) {
            props.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.imap.ssl.enable", "true");
        }

        props.put("mail.imap.starttls.enable", config.isStarttlsEnabled() ? "true" : "false");
        return props;
    }

    /**
     * Fetch emails from the specified folder.
     *
     * @param folderName Name of the folder (e.g., "INBOX")
     * @throws EmailConnectorException if fetching fails
     */
    public Message[] fetchEmails(String folderName) throws EmailConnectorException {
        try {
            Folder folder = store.getFolder(folderName);
            folder.open(Folder.READ_ONLY);
            Message[] messages = folder.getMessages();
            System.out.println("Fetched " + messages.length + " emails from " + folderName);
            return messages;
        } catch (MessagingException e) {
            throw new EmailConnectorException("Failed to fetch emails: " + e.getMessage(), e);
        }
    }

    /**
     * Disconnect from the IMAP server.
     *
     * @throws EmailConnectorException if disconnection fails
     */
    public void disconnect() throws EmailConnectorException {
        try {
            if (store != null && store.isConnected()) {
                store.close();
                System.out.println("Disconnected from IMAP server");
            }
        } catch (MessagingException e) {
            throw new EmailConnectorException("Failed to disconnect: " + e.getMessage(), e);
        }
    }
}