package com.example.usermanagement;

import java.util.EnumSet;
import java.util.Set;

/**
 * Free tier user with limited permissions and no billing.
 * Features:
 * - Basic read and write access only
 * - Maximum 10 operations per day
 * - No 2FA support
 * - Free tier ($0.00)
 */
public class FreeUser extends User {
    private static final Set<Permission> FREE_PERMISSIONS = EnumSet.of(
        Permission.READ,
        Permission.BASIC_WRITE
    );

    /**
     * Constructor for FreeUser.
     * 
     * @param username The username
     * @param email The email address
     * @param password The plain text password
     */
    public FreeUser(String username, String email, String password) {
        super(username, email, password);
        this.maxDailyOperations = 10;
    }

    @Override
    protected boolean checkPermission(Permission permission) {
        return FREE_PERMISSIONS.contains(permission);
    }

    @Override
    public String calculateBilling() {
        return "Free Tier: $0.00/month - No subscription required";
    }

    @Override
    public String getUserType() {
        return "Free User";
    }

    @Override
    protected Set<Permission> getAvailablePermissions() {
        return FREE_PERMISSIONS;
    }

    /**
     * Free users do not support 2FA.
     * 
     * @return always false
     */
    public boolean supports2FA() {
        return false;
    }
}
