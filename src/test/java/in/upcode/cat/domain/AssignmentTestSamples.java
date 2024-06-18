package in.upcode.cat.domain;

import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class AssignmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Assignment getAssignmentSample1() {
        return new Assignment()
            .id("id1")
            .question("question1")
            .description("description1")
            .image(null)
            .url("url1")
            .urlType("urlType1")
            .technology("technology1")
            .difficultyLevel("difficultyLevel1")
            .timeLimit(null)
            .type(new Category().id("sampleCategoryId1").assignmentType("sampleType1").image(null))
            .evaluationType("evaluationType1")
            .maxPoints(1);
    }

    public static Assignment getAssignmentSample2() {
        return new Assignment()
            .id("id2")
            .question("question2")
            .description("description2")
            .image(null)
            .url("url2")
            .urlType("urlType2")
            .technology("technology2")
            .difficultyLevel("difficultyLevel2")
            .timeLimit(null)
            .type(new Category().id("sampleCategoryId2").assignmentType("sampleType2").image(null))
            .evaluationType("evaluationType2")
            .maxPoints(2);
    }

    public static Assignment getAssignmentRandomSampleGenerator() {
        Assignment sample = new Assignment();
        sample.setId(UUID.randomUUID().toString());
        sample.setQuestion(UUID.randomUUID().toString());
        sample.setDescription(UUID.randomUUID().toString());
        sample.setImage(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
        sample.setUrl(UUID.randomUUID().toString());
        sample.setUrlType(UUID.randomUUID().toString());
        sample.setTechnology(UUID.randomUUID().toString());
        sample.setDifficultyLevel(UUID.randomUUID().toString());
        sample.setTimeLimit(LocalTime.now());
        sample.setType(
            new Category()
                .id(UUID.randomUUID().toString())
                .assignmentType(UUID.randomUUID().toString())
                .image(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8))
        );
        sample.setEvaluationType(UUID.randomUUID().toString());
        sample.setMaxPoints((int) (Math.random() * 100));

        return sample;
    }
}
