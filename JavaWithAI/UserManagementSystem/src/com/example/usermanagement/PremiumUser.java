package com.example.usermanagement;

import java.util.EnumSet;
import java.util.Set;

/**
 * Premium tier user with extended permissions and subscription billing.
 * Features:
 * - Extended permissions (READ, WRITE, DELETE, EXPORT)
 * - Maximum 100 operations per day
 * - Optional 2FA support
 * - Session timeout: 30 minutes
 * - Monthly subscription: $9.99/month
 */
public class PremiumUser extends User {
    private static final Set<Permission> PREMIUM_PERMISSIONS = EnumSet.of(
        Permission.READ,
        Permission.WRITE,
        Permission.DELETE,
        Permission.EXPORT
    );

    private boolean twoFactorEnabled;
    private static final int SESSION_TIMEOUT_MINUTES = 30;

    /**
     * Constructor for PremiumUser.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password
     * @param twoFactorEnabled Whether 2FA is enabled (optional)
     */
    public PremiumUser(String username, String email, String password, boolean twoFactorEnabled) {
        super(username, email, password);
        this.maxDailyOperations = 100;
        this.twoFactorEnabled = twoFactorEnabled;
    }

    /**
     * Constructor for PremiumUser without 2FA.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password
     */
    public PremiumUser(String username, String email, String password) {
        this(username, email, password, false);
    }

    @Override
    protected boolean checkPermission(Permission permission) {
        // Premium users have limited admin access (own resources only)
        if (permission == Permission.ADMIN) {
            return true; // Limited admin access
        }
        return PREMIUM_PERMISSIONS.contains(permission);
    }

    @Override
    public boolean authenticate(String password) {
        boolean baseAuth = super.authenticate(password);
        if (!baseAuth) {
            return false;
        }
        
        // If 2FA is enabled, it would be verified here
        // For demo purposes, we'll just check if it's enabled
        if (twoFactorEnabled) {
            // In real implementation, verify 2FA code here
            return true; // Simplified for demo
        }
        return true;
    }

    @Override
    public String calculateBilling() {
        return String.format(
            "Premium Subscription: $9.99/month\n" +
            "Annual Option: $99.99/year (Save $19.89)\n" +
            "2FA Enabled: %s",
            twoFactorEnabled ? "Yes" : "No"
        );
    }

    @Override
    public String getUserType() {
        return "Premium User";
    }

    @Override
    protected Set<Permission> getAvailablePermissions() {
        return PREMIUM_PERMISSIONS;
    }

    /**
     * Gets the session timeout in minutes.
     * 
     * @return Session timeout in minutes
     */
    public int getSessionTimeout() {
        return SESSION_TIMEOUT_MINUTES;
    }

    /**
     * Checks if 2FA is enabled.
     * 
     * @return true if 2FA is enabled
     */
    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    /**
     * Enables 2FA for this user.
     */
    public void enable2FA() {
        this.twoFactorEnabled = true;
    }
}
