package com.example.notificationsystem;

import java.util.Optional;

/**
 * Contract for a notification channel. Each implementation provides
 * channel-specific message formatting, delivery confirmation, and retry logic.
 */
public interface NotificationChannel {

    /**
     * Sends a notification to the given recipient.
     *
     * @param message   the raw message content
     * @param recipient channel-specific recipient (email address, phone, user id, etc.)
     * @return true if the notification was accepted for delivery
     */
    boolean sendNotification(String message, String recipient);

    /**
     * Formats the message for this channel (e.g. subject + body for email, truncation for SMS).
     *
     * @param message raw message content
     * @return formatted message suitable for this channel
     */
    String formatMessage(String message);

    /**
     * Confirms whether a previously sent message was delivered (channel-specific semantics).
     *
     * @param messageId identifier returned or tracked when sending
     * @return true if delivery is confirmed
     */
    boolean confirmDelivery(String messageId);

    /**
     * Retries sending or re-checks delivery for a message (channel-specific retry logic).
     *
     * @param messageId identifier of the message to retry
     * @return true if retry succeeded or message is now confirmed
     */
    default boolean retryMessage(String messageId) {
        return retryMessage(messageId, Integer.MAX_VALUE);
    }

    /**
     * Retries sending or re-checks delivery for a message with caller-controlled max attempts.
     *
     * @param messageId   identifier of the message to retry
     * @param maxRetries  maximum number of retry attempts (channel may cap with its own limit)
     * @return true if retry succeeded or message is now confirmed
     */
    boolean retryMessage(String messageId, int maxRetries);

    /**
     * Human-readable channel type (e.g. "Email", "SMS", "Push", "Slack").
     */
    String getChannelType();

    /**
     * Whether this channel is currently available for sending.
     */
    boolean isAvailable();

    /**
     * Returns the last message id sent to the given recipient (if any).
     * Enables callers to confirm/retry without holding channel references.
     *
     * @param recipient recipient identifier (e.g. email, phone)
     * @return last message id for that recipient, or empty if none
     */
    Optional<String> getLastMessageId(String recipient);
}
