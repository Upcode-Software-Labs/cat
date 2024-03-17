package in.upcode.cat.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AssessmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Assessment getAssessmentSample1() {
        return new Assessment()
            .id("id1")
            .title("title1")
            .languageFramework("languageFramework1")
            .difficultyLevel("difficultyLevel1")
            .timeLimit(1)
            .type("type1")
            .validationCriteria("validationCriteria1")
            .question("question1")
            .maxPoints(1);
    }

    public static Assessment getAssessmentSample2() {
        return new Assessment()
            .id("id2")
            .title("title2")
            .languageFramework("languageFramework2")
            .difficultyLevel("difficultyLevel2")
            .timeLimit(2)
            .type("type2")
            .validationCriteria("validationCriteria2")
            .question("question2")
            .maxPoints(2);
    }

    public static Assessment getAssessmentRandomSampleGenerator() {
        return new Assessment()
            .id(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .languageFramework(UUID.randomUUID().toString())
            .difficultyLevel(UUID.randomUUID().toString())
            .timeLimit(intCount.incrementAndGet())
            .type(UUID.randomUUID().toString())
            .validationCriteria(UUID.randomUUID().toString())
            .question(UUID.randomUUID().toString())
            .maxPoints(intCount.incrementAndGet());
    }
}
