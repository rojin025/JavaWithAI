package com.example.notificationsystem;

import java.time.Instant;

/**
 * Tracks status of a notification message: id, state, recipient, channel type, and timestamp.
 * Enables clear lifecycle and when confirm/retry is allowed.
 */
public final class MessageStatus {

    private final String messageId;
    private final String recipient;
    private final String channelType;
    private final Instant createdAt;
    private volatile MessageState state;

    public MessageStatus(String messageId, String recipient, String channelType, MessageState state) {
        this.messageId = messageId;
        this.recipient = recipient;
        this.channelType = channelType;
        this.state = state;
        this.createdAt = Instant.now();
    }

    public String getMessageId() {
        return messageId;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getChannelType() {
        return channelType;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
