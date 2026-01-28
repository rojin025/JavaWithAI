package com.example.notificationsystem;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base implementation of {@link NotificationChannel} with common flow:
 * format message → send → track status (MessageState) → confirm/retry with template method.
 * Retry loop, backoff, and status updates live here; channels implement doSend and doRetry (one attempt).
 */
public abstract class AbstractNotificationChannel implements NotificationChannel {

    /** Tracks last message id per recipient for getLastMessageId. */
    private final Map<String, String> lastMessageIdByRecipient = new ConcurrentHashMap<>();
    /** Tracks message status for lifecycle (SENT, DELIVERED, FAILED). */
    private final Map<String, MessageStatus> statusByMessageId = new ConcurrentHashMap<>();

    @Override
    public final boolean sendNotification(String message, String recipient) {
        if (!isAvailable()) {
            return false;
        }
        String formatted = formatMessage(message);
        String messageId = doSend(formatted, recipient);
        if (messageId != null) {
            lastMessageIdByRecipient.put(recipient, messageId);
            statusByMessageId.put(messageId, new MessageStatus(messageId, recipient, getChannelType(), MessageState.SENT));
            return true;
        }
        return false;
    }

    /**
     * Channel-specific send. Returns a message id for confirmation/retry, or null if send failed.
     */
    protected abstract String doSend(String formattedMessage, String recipient);

    @Override
    public final boolean retryMessage(String messageId, int maxRetries) {
        if (!isAvailable() || messageId == null) {
            return false;
        }
        MessageStatus status = statusByMessageId.get(messageId);
        if (status == null || status.getState() == MessageState.DELIVERED || status.getState() == MessageState.FAILED) {
            return status != null && status.getState() == MessageState.DELIVERED;
        }
        int effectiveMax = Math.max(0, maxRetries);
        for (int attempt = 0; attempt < effectiveMax; attempt++) {
            doRetry(messageId);
            if (confirmDelivery(messageId)) {
                status.setState(MessageState.DELIVERED);
                return true;
            }
            if (attempt < effectiveMax - 1) {
                try {
                    Thread.sleep(100L * (attempt + 1));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    status.setState(MessageState.FAILED);
                    return false;
                }
            }
        }
        status.setState(MessageState.FAILED);
        return false;
    }

    @Override
    public final boolean confirmDelivery(String messageId) {
        boolean result = doConfirmDelivery(messageId);
        if (result && messageId != null) {
            getMessageStatus(messageId).ifPresent(s -> s.setState(MessageState.DELIVERED));
        }
        return result;
    }

    /**
     * Channel-specific delivery confirmation (e.g. check external API or id prefix).
     *
     * @param messageId message to confirm
     * @return true if delivery is confirmed
     */
    protected abstract boolean doConfirmDelivery(String messageId);

    /**
     * Channel-specific single retry attempt (or re-check delivery). Base runs loop with backoff.
     *
     * @param messageId message to retry
     * @return true if this attempt succeeded (base will also call confirmDelivery)
     */
    protected abstract boolean doRetry(String messageId);

    @Override
    public Optional<String> getLastMessageId(String recipient) {
        return Optional.ofNullable(lastMessageIdByRecipient.get(recipient));
    }

    /**
     * Gets message status for a message id (for tests or manager).
     */
    protected Optional<MessageStatus> getMessageStatus(String messageId) {
        return Optional.ofNullable(statusByMessageId.get(messageId));
    }
}
