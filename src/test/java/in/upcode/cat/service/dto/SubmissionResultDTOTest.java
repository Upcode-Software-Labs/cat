package in.upcode.cat.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubmissionResultDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubmissionResultDTO.class);
        SubmissionResultDTO submissionResultDTO1 = new SubmissionResultDTO();
        submissionResultDTO1.setId("id1");
        SubmissionResultDTO submissionResultDTO2 = new SubmissionResultDTO();
        assertThat(submissionResultDTO1).isNotEqualTo(submissionResultDTO2);
        submissionResultDTO2.setId(submissionResultDTO1.getId());
        assertThat(submissionResultDTO1).isEqualTo(submissionResultDTO2);
        submissionResultDTO2.setId("id2");
        assertThat(submissionResultDTO1).isNotEqualTo(submissionResultDTO2);
        submissionResultDTO1.setId(null);
        assertThat(submissionResultDTO1).isNotEqualTo(submissionResultDTO2);
    }
}
