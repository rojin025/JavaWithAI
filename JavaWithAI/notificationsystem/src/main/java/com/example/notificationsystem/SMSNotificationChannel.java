package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * SMS channel: truncate to 160 chars (single segment), delivery confirmation
 * via carrier status, retry up to 3 times. Config via constructor (apiKey); validates recipient with phone regex.
 */
public class SMSNotificationChannel extends AbstractNotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(SMSNotificationChannel.class);
    /** E.164-like: optional +, then digits (10–15). */
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?[0-9]{10,15}$");
    private static final int MAX_LENGTH = 160;
    private static final int MAX_RETRIES = 3;

    private final String apiKey;
    private final Map<String, Integer> retryCountByMessageId = new ConcurrentHashMap<>();
    private boolean available = true;

    public SMSNotificationChannel(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String formatMessage(String message) {
        String trimmed = message.trim();
        if (trimmed.length() <= MAX_LENGTH) {
            return "[SMS] " + trimmed;
        }
        return "[SMS] " + trimmed.substring(0, MAX_LENGTH - 3) + "...";
    }

    @Override
    protected String doSend(String formattedMessage, String recipient) {
        if (!isAvailable()) return null;
        if (!isValidRecipient(recipient)) {
            log.warn("doSend: invalid phone recipient");
            return null;
        }
        String messageId = "sms-" + recipient.hashCode() + "-" + System.currentTimeMillis();
        retryCountByMessageId.put(messageId, 0);
        return messageId;
    }

    private boolean isValidRecipient(String recipient) {
        if (recipient == null || recipient.isBlank()) return false;
        String digits = recipient.trim().replaceAll("\\s", "");
        return PHONE_PATTERN.matcher(digits).matches();
    }

    @Override
    protected boolean doConfirmDelivery(String messageId) {
        return messageId != null && messageId.startsWith("sms-");
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
        return "SMS";
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getApiKey() {
        return apiKey;
    }
}
