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
            .videoExplanation("videoExplanation1")
            .textDescription("textDescription1")
            .pointsScored(1);
    }

    public static Submission getSubmissionSample2() {
        return new Submission()
            .id("id2")
            .githubUrl("githubUrl2")
            .videoExplanation("videoExplanation2")
            .textDescription("textDescription2")
            .pointsScored(2);
    }

    public static Submission getSubmissionRandomSampleGenerator() {
        return new Submission()
            .id(UUID.randomUUID().toString())
            .githubUrl(UUID.randomUUID().toString())
            .videoExplanation(UUID.randomUUID().toString())
            .textDescription(UUID.randomUUID().toString())
            .pointsScored(intCount.incrementAndGet());
    }
}
