package com.example.services;

import com.example.models.User;
import com.example.exceptions.AuditServiceException;

public class AuditService {
    
    public void logUserCreation(User user) throws AuditServiceException {
        // Validate user
        if (user == null) {
            throw new AuditServiceException("User cannot be null");
        }
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new AuditServiceException("User ID is required");
        }
        
        // Simulate logging user creation
        System.out.println("Logging user creation event...");
        System.out.println("User ID: " + user.getId());
        System.out.println("User Email: " + user.getEmail());
        System.out.println("User Name: " + user.getName());
        System.out.println("Created At: " + user.getCreatedAt());
        
        // Simulate some processing time
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("User creation logged successfully");
    }
} 