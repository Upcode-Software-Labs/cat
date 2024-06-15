package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SubmissionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Submission getSubmissionSample1() {
        return new Submission()
            .id("id1")
            .githubUrl("githubUrl1")
            .screenshots(null)
            .textDescription("textDescription1")
            .timeTaken(null)
            .feedback("feedback1")
            .pointsScored(1)
            .forAssignment(
                new UserAssignment().id("sampleUserAssignmentId1").status(null).assignedAt(null).deadline(null).user(null).assignment(null)
            )
            .user(null)
            .assignment(null);
    }

    public static Submission getSubmissionSample2() {
        return new Submission()
            .id("id2")
            .githubUrl("githubUrl2")
            .screenshots(null)
            .textDescription("textDescription2")
            .timeTaken(null)
            .feedback("feedback2")
            .pointsScored(1)
            .forAssignment(
                new UserAssignment().id("sampleUserAssignmentId2").status(null).assignedAt(null).deadline(null).user(null).assignment(null)
            )
            .user(null)
            .assignment(null);
    }

    public static Submission getSubmissionRandomSampleGenerator() {
        return new Submission()
            .id(UUID.randomUUID().toString())
            .githubUrl(UUID.randomUUID().toString())
            .screenshots(null)
            .textDescription(UUID.randomUUID().toString())
            .timeTaken(null)
            .feedback(null)
            .pointsScored(intCount.incrementAndGet())
            .forAssignment(null)
            .user(null)
            .assignment(null);
    }
}
