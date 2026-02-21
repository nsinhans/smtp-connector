package com.example.email;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing an email message with attachments.
 */
public class EmailMessage {
    private String sender;
    private List<String> recipients;
    private List<String> ccRecipients;
    private List<String> bccRecipients;
    private String subject;
    private String body;
    private List<String> attachmentPaths;
    private boolean htmlBody;

    public EmailMessage() {
        this.recipients = new ArrayList<>();
        this.ccRecipients = new ArrayList<>();
        this.bccRecipients = new ArrayList<>();
        this.attachmentPaths = new ArrayList<>();
        this.htmlBody = false;
    }

    public EmailMessage(String sender, List<String> recipients, String subject, String body) {
        this();
        this.sender = sender;
        this.recipients = recipients;
        this.subject = subject;
        this.body = body;
    }

    // Getters and Setters
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<String> recipients) {
        this.recipients = recipients;
    }

    public void addRecipient(String recipient) {
        this.recipients.add(recipient);
    }

    public List<String> getCcRecipients() {
        return ccRecipients;
    }

    public void setCcRecipients(List<String> ccRecipients) {
        this.ccRecipients = ccRecipients;
    }

    public void addCcRecipient(String ccRecipient) {
        this.ccRecipients.add(ccRecipient);
    }

    public List<String> getBccRecipients() {
        return bccRecipients;
    }

    public void setBccRecipients(List<String> bccRecipients) {
        this.bccRecipients = bccRecipients;
    }

    public void addBccRecipient(String bccRecipient) {
        this.bccRecipients.add(bccRecipient);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getAttachmentPaths() {
        return attachmentPaths;
    }

    public void setAttachmentPaths(List<String> attachmentPaths) {
        this.attachmentPaths = attachmentPaths;
    }

    public void addAttachment(String attachmentPath) {
        this.attachmentPaths.add(attachmentPath);
    }

    public boolean isHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(boolean htmlBody) {
        this.htmlBody = htmlBody;
    }
}