package com.example.notificationsystem;

/**
 * Explicit lifecycle state for a sent notification message.
 * Used for clear semantics: when confirm/retry is allowed and when a message is final.
 */
public enum MessageState {
    /** Message not yet sent (e.g. queued). */
    PENDING,
    /** Message accepted for delivery; confirm/retry apply. */
    SENT,
    /** Delivery confirmed. */
    DELIVERED,
    /** Max retries exceeded or unrecoverable failure. */
    FAILED
}
