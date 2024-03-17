package in.upcode.cat.domain;

import static in.upcode.cat.domain.AssessmentTestSamples.*;
import static in.upcode.cat.domain.QuestionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class QuestionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Question.class);
        Question question1 = getQuestionSample1();
        Question question2 = new Question();
        assertThat(question1).isNotEqualTo(question2);

        question2.setId(question1.getId());
        assertThat(question1).isEqualTo(question2);

        question2 = getQuestionSample2();
        assertThat(question1).isNotEqualTo(question2);
    }

    @Test
    void assessmentTest() throws Exception {
        Question question = getQuestionRandomSampleGenerator();
        Assessment assessmentBack = getAssessmentRandomSampleGenerator();

        question.setAssessment(assessmentBack);
        assertThat(question.getAssessment()).isEqualTo(assessmentBack);

        question.assessment(null);
        assertThat(question.getAssessment()).isNull();
    }
}
