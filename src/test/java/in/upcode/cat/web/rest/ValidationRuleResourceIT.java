package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.ValidationRule;
import in.upcode.cat.repository.ValidationRuleRepository;
import in.upcode.cat.service.dto.ValidationRuleDTO;
import in.upcode.cat.service.mapper.ValidationRuleMapper;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link ValidationRuleResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ValidationRuleResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_SCRIPT = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_SCRIPT = "BBBBBBBBBB";

    private static final String DEFAULT_RULE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_RULE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/validation-rules";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private ValidationRuleRepository validationRuleRepository;

    @Autowired
    private ValidationRuleMapper validationRuleMapper;

    @Autowired
    private MockMvc restValidationRuleMockMvc;

    private ValidationRule validationRule;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidationRule createEntity() {
        ValidationRule validationRule = new ValidationRule()
            .description(DEFAULT_DESCRIPTION)
            .validationScript(DEFAULT_VALIDATION_SCRIPT)
            .ruleType(DEFAULT_RULE_TYPE);
        return validationRule;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValidationRule createUpdatedEntity() {
        ValidationRule validationRule = new ValidationRule()
            .description(UPDATED_DESCRIPTION)
            .validationScript(UPDATED_VALIDATION_SCRIPT)
            .ruleType(UPDATED_RULE_TYPE);
        return validationRule;
    }

    @BeforeEach
    public void initTest() {
        validationRuleRepository.deleteAll();
        validationRule = createEntity();
    }

    @Test
    void createValidationRule() throws Exception {
        int databaseSizeBeforeCreate = validationRuleRepository.findAll().size();
        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);
        restValidationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeCreate + 1);
        ValidationRule testValidationRule = validationRuleList.get(validationRuleList.size() - 1);
        assertThat(testValidationRule.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testValidationRule.getValidationScript()).isEqualTo(DEFAULT_VALIDATION_SCRIPT);
        assertThat(testValidationRule.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
    }

    @Test
    void createValidationRuleWithExistingId() throws Exception {
        // Create the ValidationRule with an existing ID
        validationRule.setId("existing_id");
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        int databaseSizeBeforeCreate = validationRuleRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restValidationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkRuleTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = validationRuleRepository.findAll().size();
        // set the field null
        validationRule.setRuleType(null);

        // Create the ValidationRule, which fails.
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        restValidationRuleMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllValidationRules() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        // Get all the validationRuleList
        restValidationRuleMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(validationRule.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].validationScript").value(hasItem(DEFAULT_VALIDATION_SCRIPT.toString())))
            .andExpect(jsonPath("$.[*].ruleType").value(hasItem(DEFAULT_RULE_TYPE)));
    }

    @Test
    void getValidationRule() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        // Get the validationRule
        restValidationRuleMockMvc
            .perform(get(ENTITY_API_URL_ID, validationRule.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(validationRule.getId()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.validationScript").value(DEFAULT_VALIDATION_SCRIPT.toString()))
            .andExpect(jsonPath("$.ruleType").value(DEFAULT_RULE_TYPE));
    }

    @Test
    void getNonExistingValidationRule() throws Exception {
        // Get the validationRule
        restValidationRuleMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingValidationRule() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();

        // Update the validationRule
        ValidationRule updatedValidationRule = validationRuleRepository.findById(validationRule.getId()).orElseThrow();
        updatedValidationRule.description(UPDATED_DESCRIPTION).validationScript(UPDATED_VALIDATION_SCRIPT).ruleType(UPDATED_RULE_TYPE);
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(updatedValidationRule);

        restValidationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, validationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isOk());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
        ValidationRule testValidationRule = validationRuleList.get(validationRuleList.size() - 1);
        assertThat(testValidationRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testValidationRule.getValidationScript()).isEqualTo(UPDATED_VALIDATION_SCRIPT);
        assertThat(testValidationRule.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
    }

    @Test
    void putNonExistingValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, validationRuleDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateValidationRuleWithPatch() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();

        // Update the validationRule using partial update
        ValidationRule partialUpdatedValidationRule = new ValidationRule();
        partialUpdatedValidationRule.setId(validationRule.getId());

        partialUpdatedValidationRule.description(UPDATED_DESCRIPTION).validationScript(UPDATED_VALIDATION_SCRIPT);

        restValidationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValidationRule))
            )
            .andExpect(status().isOk());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
        ValidationRule testValidationRule = validationRuleList.get(validationRuleList.size() - 1);
        assertThat(testValidationRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testValidationRule.getValidationScript()).isEqualTo(UPDATED_VALIDATION_SCRIPT);
        assertThat(testValidationRule.getRuleType()).isEqualTo(DEFAULT_RULE_TYPE);
    }

    @Test
    void fullUpdateValidationRuleWithPatch() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();

        // Update the validationRule using partial update
        ValidationRule partialUpdatedValidationRule = new ValidationRule();
        partialUpdatedValidationRule.setId(validationRule.getId());

        partialUpdatedValidationRule
            .description(UPDATED_DESCRIPTION)
            .validationScript(UPDATED_VALIDATION_SCRIPT)
            .ruleType(UPDATED_RULE_TYPE);

        restValidationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedValidationRule.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedValidationRule))
            )
            .andExpect(status().isOk());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
        ValidationRule testValidationRule = validationRuleList.get(validationRuleList.size() - 1);
        assertThat(testValidationRule.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testValidationRule.getValidationScript()).isEqualTo(UPDATED_VALIDATION_SCRIPT);
        assertThat(testValidationRule.getRuleType()).isEqualTo(UPDATED_RULE_TYPE);
    }

    @Test
    void patchNonExistingValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, validationRuleDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamValidationRule() throws Exception {
        int databaseSizeBeforeUpdate = validationRuleRepository.findAll().size();
        validationRule.setId(UUID.randomUUID().toString());

        // Create the ValidationRule
        ValidationRuleDTO validationRuleDTO = validationRuleMapper.toDto(validationRule);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restValidationRuleMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(validationRuleDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ValidationRule in the database
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteValidationRule() throws Exception {
        // Initialize the database
        validationRuleRepository.save(validationRule);

        int databaseSizeBeforeDelete = validationRuleRepository.findAll().size();

        // Delete the validationRule
        restValidationRuleMockMvc
            .perform(delete(ENTITY_API_URL_ID, validationRule.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ValidationRule> validationRuleList = validationRuleRepository.findAll();
        assertThat(validationRuleList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
