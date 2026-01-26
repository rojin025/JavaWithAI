package com.example.exceptions;

public class AuditServiceException extends Exception {
    public AuditServiceException(String message) {
        super(message);
    }
    
    public AuditServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
