package in.upcode.cat.domain;

import java.util.UUID;

public class UserAssessmentTestSamples {

    public static UserAssessment getUserAssessmentSample1() {
        return new UserAssessment().id("id1");
    }

    public static UserAssessment getUserAssessmentSample2() {
        return new UserAssessment().id("id2");
    }

    public static UserAssessment getUserAssessmentRandomSampleGenerator() {
        return new UserAssessment().id(UUID.randomUUID().toString());
    }
}
