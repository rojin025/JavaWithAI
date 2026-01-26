package com.example.services;

import com.example.models.User;
import com.example.exceptions.EmailServiceException;

public class EmailService {
    
    public void sendWelcomeEmail(User user) throws EmailServiceException {
        // Validate user
        if (user == null) {
            throw new EmailServiceException("User cannot be null");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new EmailServiceException("User email is required");
        }
        
        // Simulate sending welcome email
        System.out.println("Sending welcome email to: " + user.getEmail());
        System.out.println("Subject: Welcome to our platform, " + user.getName() + "!");
        System.out.println("Body: Thank you for joining us. Your account has been created successfully.");
        
        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Welcome email sent successfully to " + user.getEmail());
    }
} 