package com.example.email;

/**
 * Custom exception for email connector operations.
 */
public class EmailConnectorException extends Exception {
    public EmailConnectorException(String message) {
        super(message);
    }

    public EmailConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
}