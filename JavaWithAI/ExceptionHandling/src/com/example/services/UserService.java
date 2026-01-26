package com.example.services;

import com.example.models.User;
import com.example.models.UserData;
import com.example.exceptions.UserServiceException;
import java.util.UUID;

public class UserService {
    
    public User createUser(UserData userData) throws UserServiceException {
        // Validate user data
        if (userData.getEmail() == null || userData.getEmail().trim().isEmpty()) {
            throw new UserServiceException("Email is required");
        }
        if (userData.getName() == null || userData.getName().trim().isEmpty()) {
            throw new UserServiceException("Name is required");
        }
        if (userData.getPassword() == null || userData.getPassword().trim().isEmpty()) {
            throw new UserServiceException("Password is required");
        }
        
        // Generate a unique ID for the user
        String userId = UUID.randomUUID().toString();
        
        // Create and return the user
        User user = new User(userId, userData.getEmail(), userData.getName(), userData.getPassword());
        
        System.out.println("User created with ID: " + userId);
        return user;
    }
} 