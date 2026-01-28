package com.example.notificationsystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the notification system using the manager facade only:
 * send/confirm/retry by channel type, constructor-injected config, and SLF4J logging.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        NotificationManager manager = new NotificationManager();

        // Dependency injection: channels take config in constructor (no hidden defaults)
        manager.addChannel(new EmailNotificationChannel("smtp.example.com", "noreply@example.com"));
        manager.addChannel(new SMSNotificationChannel("demo-api-key"));
        manager.addChannel(new PushNotificationChannel("push-api-key", "https://push.example.com"));
        manager.addChannel(new SlackNotificationChannel("https://hooks.slack.com/demo", "#notifications"));

        String message = "Your order has shipped!\nTrack it at example.com/track/12345";
        String recipient = "user@example.com";

        log.info("=== Notification System Demo ===\n");

        // 1. Format comparison (same raw message, different channels)
        log.info("1. Message formatting (same raw message, different channels):");
        for (NotificationChannel ch : manager.getChannels()) {
            log.info("  {}: {}", ch.getChannelType(), ch.formatMessage(message).replace("\n", " | "));
        }

        // 2. Send to all channels
        log.info("\n2. Sending to all channels:");
        manager.sendToAll(message, recipient);

        // 3. Facade API: send by type, get message id from manager, confirm/retry by type (no concrete channel refs)
        log.info("\n3. Facade: send by type, confirm/retry by type (caller needs only manager + channel type):");
        manager.sendNotification(message, recipient, "Email")
                .ifPresentOrElse(
                        messageId -> {
                            log.info("  Email sent, messageId={}", messageId);
                            boolean confirmed = manager.confirmDelivery(messageId, "Email");
                            log.info("  Email confirmDelivery: {}", confirmed);
                            boolean retried = manager.retryMessage(messageId, "Email", 5);
                            log.info("  Email retryMessage(5): {}", retried);
                        },
                        () -> log.warn("  Email send failed or channel not found")
                );

        manager.sendNotification(message, "+15551234567", "SMS")
                .ifPresentOrElse(
                        messageId -> {
                            log.info("  SMS sent, messageId={}", messageId);
                            log.info("  SMS formatMessage (truncated): {}...", 
                                    manager.getChannels().stream()
                                            .filter(ch -> "SMS".equals(ch.getChannelType()))
                                            .findFirst()
                                            .map(ch -> ch.formatMessage(message))
                                            .map(s -> s.length() > 60 ? s.substring(0, 60) : s)
                                            .orElse("(none)"));
                        },
                        () -> log.warn("  SMS send failed or channel not found")
                );

        log.info("\nDone.");
    }
}
