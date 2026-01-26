package com.example.usermanagement;

/**
 * Enum representing different types of activities that can be tracked.
 */
public enum ActivityType {
    AUTHENTICATION,      // Login/logout attempts
    PERMISSION_CHECK,    // Permission verification
    OPERATION,           // General operations
    BILLING_QUERY,      // Billing information requests
    RATE_LIMIT_HIT,     // Rate limit exceeded
    SESSION_START,      // Session started
    SESSION_END,        // Session ended
    USER_ACTION         // General user actions
}
