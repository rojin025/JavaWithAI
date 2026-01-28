package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Facade for notification channels: callers depend only on the manager and channel type.
 * Provides sendNotification(message, recipient, channelType), confirmDelivery(messageId, channelType),
 * retryMessage(messageId, channelType, maxRetries), and getLastMessageId(recipient, channelType).
 * Defensive addChannel: rejects null or unavailable channels and logs.
 */
public class NotificationManager {

    private static final Logger log = LoggerFactory.getLogger(NotificationManager.class);

    private final Map<String, NotificationChannel> channelsByType = new ConcurrentHashMap<>();

    /**
     * Adds a channel keyed by its channel type. Rejects null or unavailable channels.
     *
     * @param channel channel to add (must be non-null and available)
     * @return true if added, false if rejected (null, unavailable, or duplicate type logged)
     */
    public boolean addChannel(NotificationChannel channel) {
        if (channel == null) {
            log.warn("addChannel: rejected null channel");
            return false;
        }
        if (!channel.isAvailable()) {
            log.warn("addChannel: rejected unavailable channel type={}", channel.getChannelType());
            return false;
        }
        String type = channel.getChannelType();
        if (type == null || type.isBlank()) {
            log.warn("addChannel: rejected channel with null/blank type");
            return false;
        }
        channelsByType.put(type, channel);
        log.debug("addChannel: registered channel type={}", type);
        return true;
    }

    /**
     * Sends a notification via the channel of the given type.
     *
     * @param message     raw message content
     * @param recipient   channel-specific recipient (email, phone, etc.)
     * @param channelType channel type (e.g. "Email", "SMS", "Push", "Slack")
     * @return message id if sent, or empty if channel not found or send failed
     */
    public Optional<String> sendNotification(String message, String recipient, String channelType) {
        NotificationChannel channel = channelsByType.get(channelType);
        if (channel == null) {
            log.warn("sendNotification: no channel for type={}", channelType);
            return Optional.empty();
        }
        boolean sent = channel.sendNotification(message, recipient);
        if (!sent) {
            log.debug("sendNotification: send failed type={} recipient={}", channelType, recipient);
            return Optional.empty();
        }
        return channel.getLastMessageId(recipient);
    }

    /**
     * Confirms delivery for a message sent on the given channel type.
     *
     * @param messageId   message identifier
     * @param channelType channel type
     * @return true if delivery is confirmed
     */
    public boolean confirmDelivery(String messageId, String channelType) {
        NotificationChannel channel = channelsByType.get(channelType);
        if (channel == null) {
            log.warn("confirmDelivery: no channel for type={}", channelType);
            return false;
        }
        return channel.confirmDelivery(messageId);
    }

    /**
     * Retries a message on the given channel type with caller-controlled max attempts.
     *
     * @param messageId   message identifier
     * @param channelType channel type
     * @param maxRetries  maximum retry attempts
     * @return true if retry succeeded or delivery is now confirmed
     */
    public boolean retryMessage(String messageId, String channelType, int maxRetries) {
        NotificationChannel channel = channelsByType.get(channelType);
        if (channel == null) {
            log.warn("retryMessage: no channel for type={}", channelType);
            return false;
        }
        return channel.retryMessage(messageId, maxRetries);
    }

    /**
     * Returns the last message id sent to the recipient on the given channel type.
     *
     * @param recipient   recipient identifier
     * @param channelType channel type
     * @return last message id, or empty if none
     */
    public Optional<String> getLastMessageId(String recipient, String channelType) {
        NotificationChannel channel = channelsByType.get(channelType);
        if (channel == null) {
            return Optional.empty();
        }
        return channel.getLastMessageId(recipient);
    }

    /**
     * Sends the same message to the same recipient on all registered channels.
     */
    public void sendToAll(String message, String recipient) {
        for (NotificationChannel channel : channelsByType.values()) {
            if (channel.isAvailable()) {
                boolean sent = channel.sendNotification(message, recipient);
                log.info("sendToAll: {} {}", channel.getChannelType(), sent ? "sent" : "failed");
            } else {
                log.info("sendToAll: {} unavailable", channel.getChannelType());
            }
        }
    }

    /**
     * Returns a copy of all registered channels (e.g. for format comparison).
     */
    public List<NotificationChannel> getChannels() {
        return new ArrayList<>(channelsByType.values());
    }
}
