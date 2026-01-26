import com.example.services.UserService;
import com.example.services.EmailService;
import com.example.services.AuditService;
import com.example.services.MetricsService;
import com.example.models.User;
import com.example.models.UserData;
import com.example.exceptions.UserServiceException;
import com.example.exceptions.EmailServiceException;
import com.example.exceptions.AuditServiceException;

public class App {
    public static void main(String[] args) {
        // Initialize services
        MetricsService metricsService = new MetricsService();
        UserService userService = new UserService();
        EmailService emailService = new EmailService();
        AuditService auditService = new AuditService();
        
        // Create sample user data
        UserData userData = new UserData("john.doe@example.com", "John Doe", "password123");
        
        User user;
        try {
            user = userService.createUser(userData);
            metricsService.recordUserServiceSuccess();
        } catch (UserServiceException e) {
            metricsService.recordUserServiceFailure();
            System.out.println("ERROR [UserService]: Failed to create user - " + e.getMessage());
            System.out.println("  User creation aborted. No user was created.");
            metricsService.printMetrics();
            return;
        }
        
        try {
            emailService.sendWelcomeEmail(user);
            metricsService.recordEmailServiceSuccess();
        } catch (EmailServiceException e) {
            metricsService.recordEmailServiceFailure();
            System.out.println("ERROR [EmailService]: Failed to send welcome email - " + e.getMessage());
            System.out.println("  User was created successfully, but email notification failed.");
            // Continue execution - email failure shouldn't prevent user creation
        }
        
        try {
            auditService.logUserCreation(user);
            metricsService.recordAuditServiceSuccess();
        } catch (AuditServiceException e) {
            metricsService.recordAuditServiceFailure();
            System.out.println("ERROR [AuditService]: Failed to log user creation - " + e.getMessage());
            System.out.println("  User was created successfully, but audit logging failed.");
            // Continue execution - audit failure shouldn't prevent user creation
        }
        
        System.out.println("User created successfully!");
        
        // Print metrics report
        metricsService.printMetrics();
    }
}
