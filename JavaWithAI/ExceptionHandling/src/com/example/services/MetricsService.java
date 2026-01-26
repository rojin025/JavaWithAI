package com.example.services;

public class MetricsService {
    // UserService metrics
    private int userServiceSuccesses = 0;
    private int userServiceFailures = 0;
    
    // EmailService metrics
    private int emailServiceSuccesses = 0;
    private int emailServiceFailures = 0;
    
    // AuditService metrics
    private int auditServiceSuccesses = 0;
    private int auditServiceFailures = 0;
    
    // Recording methods for UserService
    public void recordUserServiceSuccess() {
        userServiceSuccesses++;
    }
    
    public void recordUserServiceFailure() {
        userServiceFailures++;
    }
    
    // Recording methods for EmailService
    public void recordEmailServiceSuccess() {
        emailServiceSuccesses++;
    }
    
    public void recordEmailServiceFailure() {
        emailServiceFailures++;
    }
    
    // Recording methods for AuditService
    public void recordAuditServiceSuccess() {
        auditServiceSuccesses++;
    }
    
    public void recordAuditServiceFailure() {
        auditServiceFailures++;
    }
    
    // Calculation methods for UserService
    public int getUserServiceTotalCalls() {
        return userServiceSuccesses + userServiceFailures;
    }
    
    public double getUserServiceSuccessRate() {
        int total = getUserServiceTotalCalls();
        if (total == 0) return 0.0;
        return (double) userServiceSuccesses / total * 100.0;
    }
    
    // Calculation methods for EmailService
    public int getEmailServiceTotalCalls() {
        return emailServiceSuccesses + emailServiceFailures;
    }
    
    public double getEmailServiceSuccessRate() {
        int total = getEmailServiceTotalCalls();
        if (total == 0) return 0.0;
        return (double) emailServiceSuccesses / total * 100.0;
    }
    
    // Calculation methods for AuditService
    public int getAuditServiceTotalCalls() {
        return auditServiceSuccesses + auditServiceFailures;
    }
    
    public double getAuditServiceSuccessRate() {
        int total = getAuditServiceTotalCalls();
        if (total == 0) return 0.0;
        return (double) auditServiceSuccesses / total * 100.0;
    }
    
    // Overall metrics
    public int getTotalCalls() {
        return getUserServiceTotalCalls() + getEmailServiceTotalCalls() + getAuditServiceTotalCalls();
    }
    
    public int getTotalSuccesses() {
        return userServiceSuccesses + emailServiceSuccesses + auditServiceSuccesses;
    }
    
    public int getTotalFailures() {
        return userServiceFailures + emailServiceFailures + auditServiceFailures;
    }
    
    public double getOverallSuccessRate() {
        int total = getTotalCalls();
        if (total == 0) return 0.0;
        return (double) getTotalSuccesses() / total * 100.0;
    }
    
    // Reporting methods
    public void printMetrics() {
        System.out.println(getMetricsReport());
    }
    
    public String getMetricsReport() {
        StringBuilder report = new StringBuilder();
        
        report.append("\n");
        report.append("========================================\n");
        report.append("         SERVICE METRICS REPORT\n");
        report.append("========================================\n");
        report.append("\n");
        
        // UserService metrics
        report.append("UserService:\n");
        report.append(String.format("  Successes: %d\n", userServiceSuccesses));
        report.append(String.format("  Failures: %d\n", userServiceFailures));
        report.append(String.format("  Total Calls: %d\n", getUserServiceTotalCalls()));
        report.append(String.format("  Success Rate: %.2f%%\n", getUserServiceSuccessRate()));
        report.append("\n");
        
        // EmailService metrics
        report.append("EmailService:\n");
        report.append(String.format("  Successes: %d\n", emailServiceSuccesses));
        report.append(String.format("  Failures: %d\n", emailServiceFailures));
        report.append(String.format("  Total Calls: %d\n", getEmailServiceTotalCalls()));
        report.append(String.format("  Success Rate: %.2f%%\n", getEmailServiceSuccessRate()));
        report.append("\n");
        
        // AuditService metrics
        report.append("AuditService:\n");
        report.append(String.format("  Successes: %d\n", auditServiceSuccesses));
        report.append(String.format("  Failures: %d\n", auditServiceFailures));
        report.append(String.format("  Total Calls: %d\n", getAuditServiceTotalCalls()));
        report.append(String.format("  Success Rate: %.2f%%\n", getAuditServiceSuccessRate()));
        report.append("\n");
        
        // Overall summary
        report.append("========================================\n");
        report.append(String.format("Overall Success Rate: %.2f%%\n", getOverallSuccessRate()));
        report.append(String.format("Total Calls: %d | Successes: %d | Failures: %d\n", 
            getTotalCalls(), getTotalSuccesses(), getTotalFailures()));
        report.append("========================================\n");
        
        return report.toString();
    }
    
    // Utility methods
    public void resetMetrics() {
        userServiceSuccesses = 0;
        userServiceFailures = 0;
        emailServiceSuccesses = 0;
        emailServiceFailures = 0;
        auditServiceSuccesses = 0;
        auditServiceFailures = 0;
    }
    
    // Getters for individual metrics (for testing/debugging)
    public int getUserServiceSuccesses() {
        return userServiceSuccesses;
    }
    
    public int getUserServiceFailures() {
        return userServiceFailures;
    }
    
    public int getEmailServiceSuccesses() {
        return emailServiceSuccesses;
    }
    
    public int getEmailServiceFailures() {
        return emailServiceFailures;
    }
    
    public int getAuditServiceSuccesses() {
        return auditServiceSuccesses;
    }
    
    public int getAuditServiceFailures() {
        return auditServiceFailures;
    }
}
