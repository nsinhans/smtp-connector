package com.example.email.util;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utility class for handling email attachments.
 */
public class AttachmentHandler {

    /**
     * Save attachments from a message to a directory.
     *
     * @param message The email message
     * @param saveDirectory Directory to save attachments
     * @throws IOException if file writing fails
     * @throws MessagingException if message processing fails
     */
    public static void saveAttachments(Message message, String saveDirectory) throws IOException, MessagingException {
        Object content = message.getContent();
        if (content instanceof Multipart) {
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String fileName = bodyPart.getFileName();
                if (fileName != null) {
                    saveAttachmentToFile(bodyPart, saveDirectory, fileName);
                }
            }
        }
    }

    /**
     * Save a single attachment to file.
     */
    private static void saveAttachmentToFile(BodyPart bodyPart, String saveDirectory, String fileName) throws IOException, MessagingException {
        File dir = new File(saveDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(saveDirectory, fileName);
        try (InputStream input = bodyPart.getInputStream();
             FileOutputStream output = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            System.out.println("Attachment saved: " + file.getAbsolutePath());
        }
    }
}