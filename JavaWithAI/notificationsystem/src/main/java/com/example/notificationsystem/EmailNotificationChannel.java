package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Email channel: subject + body formatting, delivery confirmation via tracking,
 * retry up to 5 times. Config via constructor (smtpServer, fromAddress); validates recipient with email regex.
 */
public class EmailNotificationChannel extends AbstractNotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(EmailNotificationChannel.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    private static final int MAX_RETRIES = 5;

    private final String smtpServer;
    private final String fromAddress;
    private final Map<String, Integer> retryCountByMessageId = new ConcurrentHashMap<>();
    private boolean available = true;

    public EmailNotificationChannel(String smtpServer, String fromAddress) {
        this.smtpServer = smtpServer;
        this.fromAddress = fromAddress;
    }

    @Override
    public String formatMessage(String message) {
        int firstLineEnd = message.indexOf('\n');
        String subject = firstLineEnd > 0 ? message.substring(0, firstLineEnd).trim() : message;
        String body = firstLineEnd > 0 ? message.substring(firstLineEnd).trim() : "";
        return "[EMAIL] Subject: " + subject + "\nBody: " + (body.isEmpty() ? "(no body)" : body);
    }

    @Override
    protected String doSend(String formattedMessage, String recipient) {
        if (!isAvailable()) return null;
        if (!isValidRecipient(recipient)) {
            log.warn("doSend: invalid email recipient");
            return null;
        }
        String messageId = "email-" + recipient.hashCode() + "-" + System.currentTimeMillis();
        retryCountByMessageId.put(messageId, 0);
        return messageId;
    }

    private boolean isValidRecipient(String recipient) {
        return recipient != null && !recipient.isBlank() && EMAIL_PATTERN.matcher(recipient.trim()).matches();
    }

    @Override
    protected boolean doConfirmDelivery(String messageId) {
        return messageId != null && messageId.startsWith("email-");
    }

    @Override
    protected boolean doRetry(String messageId) {
        int count = retryCountByMessageId.getOrDefault(messageId, 0);
        if (count >= MAX_RETRIES) return false;
        retryCountByMessageId.put(messageId, count + 1);
        return true;
    }

    @Override
    public String getChannelType() {
        return "Email";
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public String getFromAddress() {
        return fromAddress;
    }
}
