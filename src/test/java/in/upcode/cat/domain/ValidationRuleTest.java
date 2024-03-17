package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.*;
import static in.upcode.cat.domain.ValidationRuleTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValidationRuleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationRule.class);
        ValidationRule validationRule1 = getValidationRuleSample1();
        ValidationRule validationRule2 = new ValidationRule();
        assertThat(validationRule1).isNotEqualTo(validationRule2);

        validationRule2.setId(validationRule1.getId());
        assertThat(validationRule1).isEqualTo(validationRule2);

        validationRule2 = getValidationRuleSample2();
        assertThat(validationRule1).isNotEqualTo(validationRule2);
    }

    @Test
    void assessmentTest() throws Exception {
        ValidationRule validationRule = getValidationRuleRandomSampleGenerator();
        Assessment assessmentBack = getAssessmentRandomSampleGenerator();

        validationRule.setAssessment(assessmentBack);
        assertThat(validationRule.getAssessment()).isEqualTo(assessmentBack);

        validationRule.assessment(null);
        assertThat(validationRule.getAssessment()).isNull();
    }
}
