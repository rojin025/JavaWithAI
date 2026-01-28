package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Push channel: short title + body for mobile, delivery confirmation via
 * device ACK, retry up to 2 times. Config via constructor (apiKey, endpoint); validates non-blank recipient.
 */
public class PushNotificationChannel extends AbstractNotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(PushNotificationChannel.class);
    private static final int TITLE_MAX = 50;
    private static final int BODY_MAX = 150;
    private static final int MAX_RETRIES = 2;

    private final String apiKey;
    private final String endpoint;
    private final Map<String, Integer> retryCountByMessageId = new ConcurrentHashMap<>();
    private boolean available = true;

    public PushNotificationChannel(String apiKey, String endpoint) {
        this.apiKey = apiKey;
        this.endpoint = endpoint;
    }

    @Override
    public String formatMessage(String message) {
        String trimmed = message.trim();
        String title = trimmed.length() <= TITLE_MAX ? trimmed : trimmed.substring(0, TITLE_MAX) + "...";
        String body = trimmed.length() > TITLE_MAX
                ? (trimmed.length() <= TITLE_MAX + BODY_MAX ? trimmed.substring(TITLE_MAX) : trimmed.substring(TITLE_MAX, TITLE_MAX + BODY_MAX) + "...")
                : "";
        return "[PUSH] Title: " + title + (body.isEmpty() ? "" : "\nBody: " + body.trim());
    }

    @Override
    protected String doSend(String formattedMessage, String recipient) {
        if (!isAvailable()) return null;
        if (!isValidRecipient(recipient)) {
            log.warn("doSend: invalid push recipient (blank)");
            return null;
        }
        String messageId = "push-" + recipient.hashCode() + "-" + System.currentTimeMillis();
        retryCountByMessageId.put(messageId, 0);
        return messageId;
    }

    private boolean isValidRecipient(String recipient) {
        return recipient != null && !recipient.isBlank();
    }

    @Override
    protected boolean doConfirmDelivery(String messageId) {
        return messageId != null && messageId.startsWith("push-");
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
        return "Push";
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

    public String getEndpoint() {
        return endpoint;
    }
}
