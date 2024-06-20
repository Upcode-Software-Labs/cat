package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Assignment getAssessmentSample1() {
        return new Assignment()
            .id("id1")
            .technology("technology1")
            .difficultyLevel("difficultyLevel1")
            .url("url1")
            .question("question1")
            .evaluationType("type1")
            .isDeleted(false)
            .maxPoints(1);
    }

    public static Assignment getAssessmentSample2() {
        return new Assignment()
            .id("id2")
            .technology("technology2")
            .difficultyLevel("difficultyLevel2")
            .url("url2")
            .question("question2")
            .evaluationType("type2")
            .isDeleted(true)
            .maxPoints(2);
    }

    public static Assignment getAssessmentRandomSampleGenerator() {
        return new Assignment()
            .id(UUID.randomUUID().toString())
            .technology(UUID.randomUUID().toString())
            .difficultyLevel(UUID.randomUUID().toString())
            .url(UUID.randomUUID().toString())
            .question(UUID.randomUUID().toString())
            .evaluationType(UUID.randomUUID().toString())
            .maxPoints(intCount.incrementAndGet());
    }
}
