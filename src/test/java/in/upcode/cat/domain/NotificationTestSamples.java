package in.upcode.cat.domain;

import in.upcode.cat.domain.enumeration.NotificationStatus;
import in.upcode.cat.domain.enumeration.NotificationType;
import java.time.Instant;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NotificationTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Notification getNotificationSample1() {
        return new Notification()
            .id("id1")
            .message("new submission")
            .notificationType(NotificationType.SUBMISSION)
            .status(NotificationStatus.UNREAD);
    }

    public static Notification getNotificationSample2() {
        return new Notification()
            .id("id2")
            .message("new assignment")
            .notificationType(NotificationType.ASSIGNMENT)
            .status(NotificationStatus.READ);
    }
}
