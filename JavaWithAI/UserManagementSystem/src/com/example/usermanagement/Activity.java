package com.example.usermanagement;

import java.time.LocalDateTime;

/**
 * Represents a tracked activity/event in the system.
 * Used for activity tracking and audit logging.
 */
public class Activity {
    private final String username;
    private final String userType;
    private final ActivityType activityType;
    private final String description;
    private final LocalDateTime timestamp;
    private final boolean success;
    private final String details;

    /**
     * Constructor for Activity.
     * 
     * @param username The username who performed the activity
     * @param userType The type of user (Free, Premium, Enterprise)
     * @param activityType The type of activity
     * @param description Description of the activity
     * @param success Whether the activity was successful
     * @param details Additional details about the activity
     */
    public Activity(String username, String userType, ActivityType activityType, 
                   String description, boolean success, String details) {
        this.username = username;
        this.userType = userType;
        this.activityType = activityType;
        this.description = description;
        this.timestamp = LocalDateTime.now();
        this.success = success;
        this.details = details;
    }

    /**
     * Constructor with minimal parameters.
     */
    public Activity(String username, String userType, ActivityType activityType, 
                   String description, boolean success) {
        this(username, userType, activityType, description, success, "");
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getUserType() {
        return userType;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        String status = success ? "SUCCESS" : "FAILED";
        String detailStr = details.isEmpty() ? "" : " | Details: " + details;
        return String.format("[%s] %s | %s | %s | %s%s", 
            timestamp, status, userType, username, description, detailStr);
    }
}
