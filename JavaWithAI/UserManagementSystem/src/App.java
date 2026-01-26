import com.example.usermanagement.*;

/**
 * Demo application showcasing the User Management System with inheritance.
 * Demonstrates authentication, permissions, and billing for different user types.
 */
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("=== User Management System Demo ===\n");

        // Create instances of each user type
        FreeUser freeUser = new FreeUser("john_doe", "john@example.com", "password123");
        PremiumUser premiumUser = new PremiumUser("jane_smith", "jane@example.com", "securePass456", true);
        EnterpriseUser enterpriseUser = new EnterpriseUser("admin_corp", "admin@corp.com", "enterprise789", 299.99);

        // Demonstrate polymorphism - store all users in a User array
        User[] users = {freeUser, premiumUser, enterpriseUser};

        // Display user information and demonstrate features
        for (User user : users) {
            displayUserInfo(user);
            System.out.println();
        }

        // Demonstrate authentication
        System.out.println("=== Authentication Tests ===");
        testAuthentication(freeUser, "password123", "wrongPassword");
        testAuthentication(premiumUser, "securePass456", "wrongPassword");
        testAuthentication(enterpriseUser, "enterprise789", "wrongPassword");
        System.out.println();

        // Demonstrate permissions
        System.out.println("=== Permission Tests ===");
        testPermissions(freeUser);
        testPermissions(premiumUser);
        testPermissions(enterpriseUser);
        System.out.println();

        // Demonstrate rate limiting
        System.out.println("=== Rate Limiting Tests ===");
        testRateLimiting(freeUser);
        testRateLimiting(premiumUser);
        testRateLimiting(enterpriseUser);
        System.out.println();

        // Demonstrate billing
        System.out.println("=== Billing Information ===");
        for (User user : users) {
            System.out.println(user.getUserType() + " Billing:");
            System.out.println(user.calculateBilling());
            System.out.println();
        }

        // Demonstrate user-specific features
        System.out.println("=== User-Specific Features ===");
        demonstrateUserFeatures(freeUser, premiumUser, enterpriseUser);

        // Demonstrate activity tracking
        System.out.println("=== Activity Tracking ===");
        demonstrateActivityTracking(freeUser, premiumUser, enterpriseUser);
    }

    /**
     * Displays comprehensive information about a user.
     */
    private static void displayUserInfo(User user) {
        System.out.println("--- " + user.getUserType() + " ---");
        System.out.println("Username: " + user.getUsername());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Registration Date: " + user.getRegistrationDate());
        System.out.println("Max Daily Operations: " + 
            (user.getMaxDailyOperations() == Integer.MAX_VALUE ? "Unlimited" : user.getMaxDailyOperations()));
        System.out.println("Billing: " + user.calculateBilling().split("\n")[0]);
    }

    /**
     * Tests authentication for a user.
     */
    private static void testAuthentication(User user, String correctPassword, String wrongPassword) {
        System.out.println(user.getUserType() + " Authentication:");
        System.out.println("  Correct password: " + user.authenticate(correctPassword));
        System.out.println("  Wrong password: " + user.authenticate(wrongPassword));
    }

    /**
     * Tests various permissions for a user.
     */
    private static void testPermissions(User user) {
        System.out.println(user.getUserType() + " Permissions:");
        Permission[] testPermissions = {
            Permission.READ,
            Permission.WRITE,
            Permission.DELETE,
            Permission.ADMIN,
            Permission.ANALYTICS
        };

        for (Permission perm : testPermissions) {
            System.out.println("  " + perm + ": " + user.hasPermission(perm));
        }
    }

    /**
     * Tests rate limiting for a user.
     */
    private static void testRateLimiting(User user) {
        System.out.println(user.getUserType() + " Rate Limiting:");
        int maxOps = user.getMaxDailyOperations() == Integer.MAX_VALUE ? 5 : Math.min(user.getMaxDailyOperations(), 5);
        
        for (int i = 0; i < maxOps + 2; i++) {
            boolean canPerform = user.canPerformOperation();
            System.out.println("  Operation " + (i + 1) + ": " + 
                (canPerform ? "Allowed" : "Blocked (limit exceeded)"));
            if (!canPerform) break;
        }
        System.out.println("  Total operations today: " + user.getDailyOperationCount());
    }

    /**
     * Demonstrates user-specific features.
     */
    private static void demonstrateUserFeatures(FreeUser freeUser, PremiumUser premiumUser, EnterpriseUser enterpriseUser) {
        // Free User features
        System.out.println("Free User:");
        System.out.println("  2FA Support: " + freeUser.supports2FA());
        System.out.println();

        // Premium User features
        System.out.println("Premium User:");
        System.out.println("  2FA Enabled: " + premiumUser.isTwoFactorEnabled());
        System.out.println("  Session Timeout: " + premiumUser.getSessionTimeout() + " minutes");
        System.out.println();

        // Enterprise User features
        System.out.println("Enterprise User:");
        System.out.println("  2FA Enabled: " + enterpriseUser.isTwoFactorEnabled() + " (Mandatory)");
        System.out.println("  SSO Enabled: " + enterpriseUser.isSsoEnabled());
        System.out.println("  Session Timeout: " + enterpriseUser.getSessionTimeout() + " minutes");
        System.out.println("  Custom Pricing: $" + enterpriseUser.getCustomPricing() + "/month");
        
        // Demonstrate volume discount
        enterpriseUser.setCustomPricing(249.99);
        System.out.println("  After Volume Discount: $" + enterpriseUser.getCustomPricing() + "/month");
        System.out.println();
    }

    /**
     * Demonstrates activity tracking features.
     */
    private static void demonstrateActivityTracking(FreeUser freeUser, PremiumUser premiumUser, EnterpriseUser enterpriseUser) {
        ActivityTracker tracker = User.getActivityTracker();
        
        System.out.println("Total Activities Tracked: " + tracker.getTotalActivityCount());
        System.out.println();

        // Show activity statistics for each user
        System.out.println("--- Activity Statistics ---");
        System.out.println(freeUser.getActivityStatistics());
        System.out.println();
        System.out.println(premiumUser.getActivityStatistics());
        System.out.println();
        System.out.println(enterpriseUser.getActivityStatistics());
        System.out.println();

        // Show recent activities for each user
        System.out.println("--- Recent Activities (Last 5 per user) ---");
        showRecentActivities(freeUser, 5);
        showRecentActivities(premiumUser, 5);
        showRecentActivities(enterpriseUser, 5);
        System.out.println();

        // Show authentication activities
        System.out.println("--- Authentication Activities ---");
        java.util.List<Activity> authActivities = tracker.getActivitiesByType(ActivityType.AUTHENTICATION);
        for (Activity activity : authActivities) {
            System.out.println(activity);
        }
        System.out.println();

        // Show rate limit hits
        System.out.println("--- Rate Limit Events ---");
        java.util.List<Activity> rateLimitActivities = tracker.getActivitiesByType(ActivityType.RATE_LIMIT_HIT);
        if (rateLimitActivities.isEmpty()) {
            System.out.println("No rate limit events occurred.");
        } else {
            for (Activity activity : rateLimitActivities) {
                System.out.println(activity);
            }
        }
        System.out.println();

        // Show operation activities summary
        System.out.println("--- Operation Summary ---");
        java.util.List<Activity> operations = tracker.getActivitiesByType(ActivityType.OPERATION);
        System.out.println("Total operations performed: " + operations.size());
        long successfulOps = operations.stream().filter(Activity::isSuccess).count();
        System.out.println("Successful operations: " + successfulOps);
        System.out.println();

        // Show permission check activities
        System.out.println("--- Permission Check Activities ---");
        java.util.List<Activity> permissionChecks = tracker.getActivitiesByType(ActivityType.PERMISSION_CHECK);
        System.out.println("Total permission checks: " + permissionChecks.size());
        long granted = permissionChecks.stream().filter(Activity::isSuccess).count();
        long denied = permissionChecks.size() - granted;
        System.out.println("Permissions granted: " + granted);
        System.out.println("Permissions denied: " + denied);
        System.out.println();
    }

    /**
     * Shows recent activities for a user.
     */
    private static void showRecentActivities(User user, int count) {
        java.util.List<Activity> activities = user.getActivityHistory();
        int start = Math.max(0, activities.size() - count);
        
        System.out.println(user.getUserType() + " (" + user.getUsername() + ") - Last " + 
            Math.min(count, activities.size()) + " activities:");
        
        for (int i = start; i < activities.size(); i++) {
            System.out.println("  " + activities.get(i));
        }
        
        if (activities.isEmpty()) {
            System.out.println("  No activities recorded.");
        }
        System.out.println();
    }
}
