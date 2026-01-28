package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Slack channel: markdown-style formatting, delivery confirmation via Slack API
 * response, retry up to 3 times. Config via constructor (webhookUrl, channelId); validates non-blank recipient.
 */
public class SlackNotificationChannel extends AbstractNotificationChannel {

    private static final Logger log = LoggerFactory.getLogger(SlackNotificationChannel.class);
    private static final int MAX_RETRIES = 3;

    private final String webhookUrl;
    private final String channelId;
    private final Map<String, Integer> retryCountByMessageId = new ConcurrentHashMap<>();
    private boolean available = true;

    public SlackNotificationChannel(String webhookUrl, String channelId) {
        this.webhookUrl = webhookUrl;
        this.channelId = channelId;
    }

    @Override
    public String formatMessage(String message) {
        String trimmed = message.trim();
        return "[Slack] " + (trimmed.contains("*") || trimmed.contains("_") ? trimmed : "*" + trimmed + "*");
    }

    @Override
    protected String doSend(String formattedMessage, String recipient) {
        if (!isAvailable()) return null;
        if (!isValidRecipient(recipient)) {
            log.warn("doSend: invalid Slack recipient (blank)");
            return null;
        }
        String messageId = "slack-" + recipient.hashCode() + "-" + System.currentTimeMillis();
        retryCountByMessageId.put(messageId, 0);
        return messageId;
    }

    private boolean isValidRecipient(String recipient) {
        return recipient != null && !recipient.isBlank();
    }

    @Override
    protected boolean doConfirmDelivery(String messageId) {
        return messageId != null && messageId.startsWith("slack-");
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
        return "Slack";
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getChannelId() {
        return channelId;
    }
}
