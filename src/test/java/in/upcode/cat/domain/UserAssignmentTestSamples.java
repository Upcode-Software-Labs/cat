package in.upcode.cat.domain;

import java.util.UUID;

public class UserAssignmentTestSamples {

    public static UserAssignment getUserAssessmentSample1() {
        return new UserAssignment().id("id1");
    }

    public static UserAssignment getUserAssessmentSample2() {
        return new UserAssignment().id("id2");
    }

    public static UserAssignment getUserAssessmentRandomSampleGenerator() {
        return new UserAssignment().id(UUID.randomUUID().toString());
    }
}
