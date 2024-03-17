package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class QuestionTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Question getQuestionSample1() {
        return new Question().id("id1").resources("resources1").points(1);
    }

    public static Question getQuestionSample2() {
        return new Question().id("id2").resources("resources2").points(2);
    }

    public static Question getQuestionRandomSampleGenerator() {
        return new Question().id(UUID.randomUUID().toString()).resources(UUID.randomUUID().toString()).points(intCount.incrementAndGet());
    }
}
