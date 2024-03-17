package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SubmissionResultTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static SubmissionResult getSubmissionResultSample1() {
        return new SubmissionResult().id("id1").totalPoints(1);
    }

    public static SubmissionResult getSubmissionResultSample2() {
        return new SubmissionResult().id("id2").totalPoints(2);
    }

    public static SubmissionResult getSubmissionResultRandomSampleGenerator() {
        return new SubmissionResult().id(UUID.randomUUID().toString()).totalPoints(intCount.incrementAndGet());
    }
}
