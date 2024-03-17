package in.upcode.cat.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import in.upcode.cat.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValidationRuleDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValidationRuleDTO.class);
        ValidationRuleDTO validationRuleDTO1 = new ValidationRuleDTO();
        validationRuleDTO1.setId("id1");
        ValidationRuleDTO validationRuleDTO2 = new ValidationRuleDTO();
        assertThat(validationRuleDTO1).isNotEqualTo(validationRuleDTO2);
        validationRuleDTO2.setId(validationRuleDTO1.getId());
        assertThat(validationRuleDTO1).isEqualTo(validationRuleDTO2);
        validationRuleDTO2.setId("id2");
        assertThat(validationRuleDTO1).isNotEqualTo(validationRuleDTO2);
        validationRuleDTO1.setId(null);
        assertThat(validationRuleDTO1).isNotEqualTo(validationRuleDTO2);
    }
}
