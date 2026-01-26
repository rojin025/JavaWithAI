package com.example.usermanagement;

import java.util.EnumSet;
import java.util.Set;

/**
 * Enterprise tier user with full permissions and custom billing.
 * Features:
 * - Full access to all permissions including ADMIN, BULK_OPERATIONS, ANALYTICS
 * - Unlimited operations per day
 * - Mandatory 2FA
 * - SSO support
 * - Session timeout: 2 hours
 * - Custom pricing: $299.99/month base
 */
public class EnterpriseUser extends User {
    private static final Set<Permission> ENTERPRISE_PERMISSIONS = EnumSet.allOf(Permission.class);

    private boolean twoFactorEnabled;
    private boolean ssoEnabled;
    private static final int SESSION_TIMEOUT_MINUTES = 120; // 2 hours
    private double customPricing;

    /**
     * Constructor for EnterpriseUser.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password
     * @param customPricing Custom pricing amount (defaults to $299.99 if not specified)
     */
    public EnterpriseUser(String username, String email, String password, double customPricing) {
        super(username, email, password);
        this.maxDailyOperations = Integer.MAX_VALUE; // Unlimited
        this.twoFactorEnabled = true; // Mandatory
        this.ssoEnabled = true; // SSO support
        this.customPricing = customPricing > 0 ? customPricing : 299.99;
    }

    /**
     * Constructor for EnterpriseUser with default pricing.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password
     */
    public EnterpriseUser(String username, String email, String password) {
        this(username, email, password, 299.99);
    }

    @Override
    protected boolean checkPermission(Permission permission) {
        // Enterprise users have access to all permissions
        return ENTERPRISE_PERMISSIONS.contains(permission);
    }

    @Override
    public boolean authenticate(String password) {
        boolean baseAuth = super.authenticate(password);
        if (!baseAuth) {
            return false;
        }
        
        // Enterprise users require 2FA (mandatory)
        if (!twoFactorEnabled) {
            return false; // Should not happen, but safety check
        }
        
        // In real implementation, verify 2FA code here
        // For demo purposes, we'll assume it passes
        
        // SSO authentication would be handled here if using SSO
        return true;
    }

    @Override
    public String calculateBilling() {
        return String.format(
            "Enterprise Subscription: $%.2f/month (base)\n" +
            "Annual Contract: Custom pricing available\n" +
            "Volume Discounts: Available for large deployments\n" +
            "2FA: Mandatory (Enabled)\n" +
            "SSO: Supported (Enabled)",
            customPricing
        );
    }

    @Override
    public String getUserType() {
        return "Enterprise User";
    }

    @Override
    protected Set<Permission> getAvailablePermissions() {
        return ENTERPRISE_PERMISSIONS;
    }

    @Override
    public boolean canPerformOperation() {
        // Enterprise users have unlimited operations
        dailyOperationCount++;
        return true;
    }

    /**
     * Gets the session timeout in minutes.
     * 
     * @return Session timeout in minutes (2 hours)
     */
    public int getSessionTimeout() {
        return SESSION_TIMEOUT_MINUTES;
    }

    /**
     * Checks if 2FA is enabled (always true for Enterprise).
     * 
     * @return always true
     */
    public boolean isTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    /**
     * Checks if SSO is enabled.
     * 
     * @return true if SSO is enabled
     */
    public boolean isSsoEnabled() {
        return ssoEnabled;
    }

    /**
     * Gets the custom pricing for this enterprise account.
     * 
     * @return Custom pricing amount
     */
    public double getCustomPricing() {
        return customPricing;
    }

    /**
     * Sets custom pricing (for volume discounts).
     * 
     * @param customPricing The new pricing amount
     */
    public void setCustomPricing(double customPricing) {
        this.customPricing = customPricing;
    }
}
