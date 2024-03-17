package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.*;
import static in.upcode.cat.domain.UserAssessmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAssessmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAssessment.class);
        UserAssessment userAssessment1 = getUserAssessmentSample1();
        UserAssessment userAssessment2 = new UserAssessment();
        assertThat(userAssessment1).isNotEqualTo(userAssessment2);

        userAssessment2.setId(userAssessment1.getId());
        assertThat(userAssessment1).isEqualTo(userAssessment2);

        userAssessment2 = getUserAssessmentSample2();
        assertThat(userAssessment1).isNotEqualTo(userAssessment2);
    }

    @Test
    void assessmentTest() throws Exception {
        UserAssessment userAssessment = getUserAssessmentRandomSampleGenerator();
        Assessment assessmentBack = getAssessmentRandomSampleGenerator();

        userAssessment.setAssessment(assessmentBack);
        assertThat(userAssessment.getAssessment()).isEqualTo(assessmentBack);

        userAssessment.assessment(null);
        assertThat(userAssessment.getAssessment()).isNull();
    }
}
