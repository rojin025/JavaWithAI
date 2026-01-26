package com.example.usermanagement;

/**
 * Enum representing different permission levels in the system.
 * Permissions are granted based on user type (Free, Premium, Enterprise).
 */
public enum Permission {
    READ,              // Basic read access
    BASIC_WRITE,       // Limited write access (Free users)
    WRITE,             // Full write access
    DELETE,            // Delete operations
    EXPORT,            // Export data
    ADMIN,             // Administrative access
    BULK_OPERATIONS,   // Bulk operation capabilities
    ANALYTICS          // Analytics and reporting access
}
