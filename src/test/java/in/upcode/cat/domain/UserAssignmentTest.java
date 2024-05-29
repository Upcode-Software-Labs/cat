package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssignmentTestSamples.*;
import static in.upcode.cat.domain.UserAssignmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAssignment.class);
        UserAssignment userAssignment1 = getUserAssessmentSample1();
        UserAssignment userAssignment2 = new UserAssignment();
        assertThat(userAssignment1).isNotEqualTo(userAssignment2);

        userAssignment2.setId(userAssignment1.getId());
        assertThat(userAssignment1).isEqualTo(userAssignment2);

        userAssignment2 = getUserAssessmentSample2();
        assertThat(userAssignment1).isNotEqualTo(userAssignment2);
    }

    @Test
    void assessmentTest() throws Exception {
        UserAssignment userAssignment = getUserAssessmentRandomSampleGenerator();
        Assignment assignmentBack = getAssessmentRandomSampleGenerator();

        userAssignment.setAssessment(assignmentBack);
        assertThat(userAssignment.getAssessment()).isEqualTo(assignmentBack);

        userAssignment.assessment(null);
        assertThat(userAssignment.getAssessment()).isNull();
    }
}
