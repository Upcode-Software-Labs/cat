package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.*;
import static in.upcode.cat.domain.SubmissionTestSamples.*;
import static in.upcode.cat.domain.UserAssessmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubmissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Submission.class);
        Submission submission1 = getSubmissionSample1();
        Submission submission2 = new Submission();
        assertThat(submission1).isNotEqualTo(submission2);

        submission2.setId(submission1.getId());
        assertThat(submission1).isEqualTo(submission2);

        submission2 = getSubmissionSample2();
        assertThat(submission1).isNotEqualTo(submission2);
    }

    @Test
    void forAssignmentTest() throws Exception {
        Submission submission = getSubmissionRandomSampleGenerator();
        UserAssessment userAssessmentBack = getUserAssessmentRandomSampleGenerator();

        submission.setForAssignment(userAssessmentBack);
        assertThat(submission.getForAssignment()).isEqualTo(userAssessmentBack);

        submission.forAssignment(null);
        assertThat(submission.getForAssignment()).isNull();
    }

    @Test
    void assessmentTest() throws Exception {
        Submission submission = getSubmissionRandomSampleGenerator();
        Assessment assessmentBack = getAssessmentRandomSampleGenerator();

        submission.setAssessment(assessmentBack);
        assertThat(submission.getAssessment()).isEqualTo(assessmentBack);

        submission.assessment(null);
        assertThat(submission.getAssessment()).isNull();
    }
}
