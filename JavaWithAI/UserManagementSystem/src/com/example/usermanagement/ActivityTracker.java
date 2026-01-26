package com.example.usermanagement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Tracks and manages user activities in the system.
 * Provides methods to log activities and retrieve activity history.
 */
public class ActivityTracker {
    private final List<Activity> activities;
    private final int maxHistorySize;

    /**
     * Constructor with default max history size (1000 activities).
     */
    public ActivityTracker() {
        this(1000);
    }

    /**
     * Constructor with custom max history size.
     * 
     * @param maxHistorySize Maximum number of activities to keep in history
     */
    public ActivityTracker(int maxHistorySize) {
        this.activities = new ArrayList<>();
        this.maxHistorySize = maxHistorySize;
    }

    /**
     * Logs an activity.
     * 
     * @param activity The activity to log
     */
    public void logActivity(Activity activity) {
        activities.add(activity);
        
        // Remove oldest activities if history exceeds max size
        if (activities.size() > maxHistorySize) {
            activities.remove(0);
        }
    }

    /**
     * Gets all activities for a specific user.
     * 
     * @param username The username to filter by
     * @return List of activities for the user
     */
    public List<Activity> getActivitiesByUser(String username) {
        return activities.stream()
            .filter(activity -> activity.getUsername().equals(username))
            .collect(Collectors.toList());
    }

    /**
     * Gets all activities of a specific type.
     * 
     * @param activityType The activity type to filter by
     * @return List of activities of the specified type
     */
    public List<Activity> getActivitiesByType(ActivityType activityType) {
        return activities.stream()
            .filter(activity -> activity.getActivityType() == activityType)
            .collect(Collectors.toList());
    }

    /**
     * Gets all activities within a time range.
     * 
     * @param start Start time (inclusive)
     * @param end End time (inclusive)
     * @return List of activities within the time range
     */
    public List<Activity> getActivitiesByTimeRange(LocalDateTime start, LocalDateTime end) {
        return activities.stream()
            .filter(activity -> 
                !activity.getTimestamp().isBefore(start) && 
                !activity.getTimestamp().isAfter(end))
            .collect(Collectors.toList());
    }

    /**
     * Gets all activities.
     * 
     * @return List of all activities
     */
    public List<Activity> getAllActivities() {
        return new ArrayList<>(activities);
    }

    /**
     * Gets the count of successful activities for a user.
     * 
     * @param username The username
     * @return Count of successful activities
     */
    public long getSuccessCount(String username) {
        return activities.stream()
            .filter(activity -> activity.getUsername().equals(username) && activity.isSuccess())
            .count();
    }

    /**
     * Gets the count of failed activities for a user.
     * 
     * @param username The username
     * @return Count of failed activities
     */
    public long getFailureCount(String username) {
        return activities.stream()
            .filter(activity -> activity.getUsername().equals(username) && !activity.isSuccess())
            .count();
    }

    /**
     * Gets activity statistics for a user.
     * 
     * @param username The username
     * @return String containing activity statistics
     */
    public String getActivityStatistics(String username) {
        long total = activities.stream()
            .filter(activity -> activity.getUsername().equals(username))
            .count();
        long success = getSuccessCount(username);
        long failed = getFailureCount(username);
        
        if (total == 0) {
            return String.format("No activities found for user: %s", username);
        }
        
        double successRate = (success * 100.0) / total;
        return String.format(
            "Activity Statistics for %s:\n" +
            "  Total Activities: %d\n" +
            "  Successful: %d (%.1f%%)\n" +
            "  Failed: %d (%.1f%%)",
            username, total, success, successRate, failed, 100.0 - successRate
        );
    }

    /**
     * Clears all activity history.
     */
    public void clearHistory() {
        activities.clear();
    }

    /**
     * Gets the total number of activities tracked.
     * 
     * @return Total activity count
     */
    public int getTotalActivityCount() {
        return activities.size();
    }
}
