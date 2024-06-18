package in.upcode.cat.domain;

import java.util.UUID;

public class UserAssignmentTestSamples {

    public static UserAssignment getUserAssignmentSample1() {
        return new UserAssignment().id("id1");
    }

    public static UserAssignment getUserAssignmentSample2() {
        return new UserAssignment().id("id2");
    }

    public static UserAssignment getUserAssignmentRandomSampleGenerator() {
        return new UserAssignment().id(UUID.randomUUID().toString());
    }
}
