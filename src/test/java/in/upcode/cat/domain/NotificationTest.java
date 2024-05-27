package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.getAssessmentSample2;
import static in.upcode.cat.domain.NotificationTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import in.upcode.cat.domain.enumeration.NotificationStatus;
import in.upcode.cat.domain.enumeration.NotificationType;
import in.upcode.cat.web.rest.TestUtil;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notification.class);
        Notification notification1 = getNotificationSample1();
        Notification notification2 = new Notification();
        assertThat(notification1).isNotEqualTo(notification2);

        // Test with different fields
        notification2.setId(notification1.getId());
        assertThat(notification1).isEqualTo(notification2);

        notification2 = getNotificationSample2();
        assertThat(notification1).isNotEqualTo(notification2);
    }
}
