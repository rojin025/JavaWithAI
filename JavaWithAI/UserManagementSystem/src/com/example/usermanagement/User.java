package com.example.usermanagement;

import java.time.LocalDate;
import java.util.Set;

/**
 * Base abstract class for all user types in the system.
 * Provides common functionality for authentication, permissions, and billing.
 */
public abstract class User {
    protected String username;
    protected String email;
    protected String hashedPassword;
    protected LocalDate registrationDate;
    protected int dailyOperationCount;
    protected int maxDailyOperations;
    
    // Shared activity tracker for all users
    private static ActivityTracker activityTracker = new ActivityTracker();

    /**
     * Constructor for User base class.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password (will be hashed)
     */
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.hashedPassword = hashPassword(password);
        this.registrationDate = LocalDate.now();
        this.dailyOperationCount = 0;
    }

    /**
     * Authenticates a user with the provided password.
     * 
     * @param password The password to verify
     * @return true if authentication succeeds, false otherwise
     */
    public boolean authenticate(String password) {
        String hashedInput = hashPassword(password);
        boolean success = hashedInput.equals(this.hashedPassword);
        
        // Track authentication attempt
        trackActivity(ActivityType.AUTHENTICATION, 
            "Authentication attempt", 
            success, 
            success ? "Password verified" : "Invalid password");
        
        return success;
    }

    /**
     * Checks if the user has a specific permission.
     * Must be implemented by subclasses.
     * 
     * @param permission The permission to check
     * @return true if user has the permission, false otherwise
     */
    public boolean hasPermission(Permission permission) {
        boolean hasPermission = checkPermission(permission);
        
        // Track permission check
        trackActivity(ActivityType.PERMISSION_CHECK,
            "Permission check: " + permission,
            hasPermission,
            hasPermission ? "Permission granted" : "Permission denied");
        
        return hasPermission;
    }

    /**
     * Internal method to check permission (implemented by subclasses).
     * 
     * @param permission The permission to check
     * @return true if user has the permission, false otherwise
     */
    protected abstract boolean checkPermission(Permission permission);

    /**
     * Calculates the billing amount for the user.
     * Must be implemented by subclasses.
     * 
     * @return A string describing the billing information
     */
    public abstract String calculateBilling();

    /**
     * Returns the user type as a string.
     * 
     * @return The user type name
     */
    public abstract String getUserType();

    /**
     * Checks if the user can perform an operation (rate limiting).
     * 
     * @return true if operation is allowed, false if daily limit exceeded
     */
    public boolean canPerformOperation() {
        if (dailyOperationCount < maxDailyOperations) {
            dailyOperationCount++;
            trackActivity(ActivityType.OPERATION, 
                "Operation performed", 
                true, 
                String.format("Operation #%d of %d", dailyOperationCount, 
                    maxDailyOperations == Integer.MAX_VALUE ? "unlimited" : maxDailyOperations));
            return true;
        }
        
        // Track rate limit hit
        trackActivity(ActivityType.RATE_LIMIT_HIT, 
            "Rate limit exceeded", 
            false, 
            String.format("Daily limit of %d operations reached", maxDailyOperations));
        return false;
    }

    /**
     * Resets the daily operation count (typically called at midnight).
     */
    public void resetDailyOperations() {
        this.dailyOperationCount = 0;
    }

    /**
     * Simple password hashing (in production, use proper hashing like BCrypt).
     * 
     * @param password Plain text password
     * @return Hashed password
     */
    protected String hashPassword(String password) {
        // Simple hash for demonstration (use proper hashing in production)
        return String.valueOf(password.hashCode());
    }

    /**
     * Gets the set of permissions available to this user type.
     * 
     * @return Set of permissions
     */
    protected abstract Set<Permission> getAvailablePermissions();

    // Getters
    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public int getDailyOperationCount() {
        return dailyOperationCount;
    }

    public int getMaxDailyOperations() {
        return maxDailyOperations;
    }

    /**
     * Tracks an activity for this user.
     * 
     * @param activityType The type of activity
     * @param description Description of the activity
     * @param success Whether the activity was successful
     * @param details Additional details
     */
    protected void trackActivity(ActivityType activityType, String description, 
                                 boolean success, String details) {
        Activity activity = new Activity(
            this.username,
            getUserType(),
            activityType,
            description,
            success,
            details
        );
        activityTracker.logActivity(activity);
    }

    /**
     * Gets the activity tracker instance.
     * 
     * @return The shared activity tracker
     */
    public static ActivityTracker getActivityTracker() {
        return activityTracker;
    }

    /**
     * Sets a custom activity tracker (useful for testing or custom tracking).
     * 
     * @param tracker The activity tracker to use
     */
    public static void setActivityTracker(ActivityTracker tracker) {
        activityTracker = tracker;
    }

    /**
     * Gets activity history for this user.
     * 
     * @return List of activities for this user
     */
    public java.util.List<Activity> getActivityHistory() {
        return activityTracker.getActivitiesByUser(this.username);
    }

    /**
     * Gets activity statistics for this user.
     * 
     * @return String containing activity statistics
     */
    public String getActivityStatistics() {
        return activityTracker.getActivityStatistics(this.username);
    }
}
