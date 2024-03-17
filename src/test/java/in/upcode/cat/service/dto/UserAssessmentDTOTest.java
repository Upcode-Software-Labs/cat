package in.upcode.cat.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserAssessmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAssessmentDTO.class);
        UserAssessmentDTO userAssessmentDTO1 = new UserAssessmentDTO();
        userAssessmentDTO1.setId("id1");
        UserAssessmentDTO userAssessmentDTO2 = new UserAssessmentDTO();
        assertThat(userAssessmentDTO1).isNotEqualTo(userAssessmentDTO2);
        userAssessmentDTO2.setId(userAssessmentDTO1.getId());
        assertThat(userAssessmentDTO1).isEqualTo(userAssessmentDTO2);
        userAssessmentDTO2.setId("id2");
        assertThat(userAssessmentDTO1).isNotEqualTo(userAssessmentDTO2);
        userAssessmentDTO1.setId(null);
        assertThat(userAssessmentDTO1).isNotEqualTo(userAssessmentDTO2);
    }
}
