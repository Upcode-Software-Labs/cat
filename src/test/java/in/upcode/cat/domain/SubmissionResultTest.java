package in.upcode.cat.domain;

import static in.upcode.cat.domain.SubmissionResultTestSamples.*;
import static in.upcode.cat.domain.SubmissionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubmissionResultTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmissionResult.class);
        SubmissionResult submissionResult1 = getSubmissionResultSample1();
        SubmissionResult submissionResult2 = new SubmissionResult();
        assertThat(submissionResult1).isNotEqualTo(submissionResult2);

        submissionResult2.setId(submissionResult1.getId());
        assertThat(submissionResult1).isEqualTo(submissionResult2);

        submissionResult2 = getSubmissionResultSample2();
        assertThat(submissionResult1).isNotEqualTo(submissionResult2);
    }

    @Test
    void submissionTest() throws Exception {
        SubmissionResult submissionResult = getSubmissionResultRandomSampleGenerator();
        Submission submissionBack = getSubmissionRandomSampleGenerator();

        submissionResult.setSubmission(submissionBack);
        assertThat(submissionResult.getSubmission()).isEqualTo(submissionBack);

        submissionResult.submission(null);
        assertThat(submissionResult.getSubmission()).isNull();
    }
}
