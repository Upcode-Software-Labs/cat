package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssessmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Assessment.class);
        Assessment assessment1 = getAssessmentSample1();
        Assessment assessment2 = new Assessment();
        assertThat(assessment1).isNotEqualTo(assessment2);

        assessment2.setId(assessment1.getId());
        assertThat(assessment1).isEqualTo(assessment2);

        assessment2 = getAssessmentSample2();
        assertThat(assessment1).isNotEqualTo(assessment2);
    }
}
