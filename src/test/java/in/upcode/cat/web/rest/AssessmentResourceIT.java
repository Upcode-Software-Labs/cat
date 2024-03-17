package in.upcode.cat.web.rest;

import static in.upcode.cat.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.Assessment;
import in.upcode.cat.repository.AssessmentRepository;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.mapper.AssessmentMapper;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
 * Integration tests for the {@link AssessmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssessmentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE_FRAMEWORK = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE_FRAMEWORK = "BBBBBBBBBB";

    private static final String DEFAULT_DIFFICULTY_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_DIFFICULTY_LEVEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIME_LIMIT = 1;
    private static final Integer UPDATED_TIME_LIMIT = 2;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDATION_CRITERIA = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_CRITERIA = "BBBBBBBBBB";

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_POINTS = 1;
    private static final Integer UPDATED_MAX_POINTS = 2;

    private static final ZonedDateTime DEFAULT_DEADLINE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DEADLINE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String ENTITY_API_URL = "/api/assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private AssessmentMapper assessmentMapper;

    @Autowired
    private MockMvc restAssessmentMockMvc;

    private Assessment assessment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assessment createEntity() {
        Assessment assessment = new Assessment()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .languageFramework(DEFAULT_LANGUAGE_FRAMEWORK)
            .difficultyLevel(DEFAULT_DIFFICULTY_LEVEL)
            .timeLimit(DEFAULT_TIME_LIMIT)
            .type(DEFAULT_TYPE)
            .validationCriteria(DEFAULT_VALIDATION_CRITERIA)
            .question(DEFAULT_QUESTION)
            .maxPoints(DEFAULT_MAX_POINTS)
            .deadline(DEFAULT_DEADLINE);
        return assessment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assessment createUpdatedEntity() {
        Assessment assessment = new Assessment()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .languageFramework(UPDATED_LANGUAGE_FRAMEWORK)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .validationCriteria(UPDATED_VALIDATION_CRITERIA)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS)
            .deadline(UPDATED_DEADLINE);
        return assessment;
    }

    @BeforeEach
    public void initTest() {
        assessmentRepository.deleteAll();
        assessment = createEntity();
    }

    @Test
    void createAssessment() throws Exception {
        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();
        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAssessment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssessment.getLanguageFramework()).isEqualTo(DEFAULT_LANGUAGE_FRAMEWORK);
        assertThat(testAssessment.getDifficultyLevel()).isEqualTo(DEFAULT_DIFFICULTY_LEVEL);
        assertThat(testAssessment.getTimeLimit()).isEqualTo(DEFAULT_TIME_LIMIT);
        assertThat(testAssessment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAssessment.getValidationCriteria()).isEqualTo(DEFAULT_VALIDATION_CRITERIA);
        assertThat(testAssessment.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testAssessment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);
        assertThat(testAssessment.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    void createAssessmentWithExistingId() throws Exception {
        // Create the Assessment with an existing ID
        assessment.setId("existing_id");
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        int databaseSizeBeforeCreate = assessmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setTitle(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setType(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkValidationCriteriaIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setValidationCriteria(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setQuestion(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMaxPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setMaxPoints(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDeadlineIsRequired() throws Exception {
        int databaseSizeBeforeTest = assessmentRepository.findAll().size();
        // set the field null
        assessment.setDeadline(null);

        // Create the Assessment, which fails.
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAssessments() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        // Get all the assessmentList
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assessment.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].languageFramework").value(hasItem(DEFAULT_LANGUAGE_FRAMEWORK)))
            .andExpect(jsonPath("$.[*].difficultyLevel").value(hasItem(DEFAULT_DIFFICULTY_LEVEL)))
            .andExpect(jsonPath("$.[*].timeLimit").value(hasItem(DEFAULT_TIME_LIMIT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].validationCriteria").value(hasItem(DEFAULT_VALIDATION_CRITERIA)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].maxPoints").value(hasItem(DEFAULT_MAX_POINTS)))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(sameInstant(DEFAULT_DEADLINE))));
    }

    @Test
    void getAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        // Get the assessment
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, assessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assessment.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.languageFramework").value(DEFAULT_LANGUAGE_FRAMEWORK))
            .andExpect(jsonPath("$.difficultyLevel").value(DEFAULT_DIFFICULTY_LEVEL))
            .andExpect(jsonPath("$.timeLimit").value(DEFAULT_TIME_LIMIT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.validationCriteria").value(DEFAULT_VALIDATION_CRITERIA))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.maxPoints").value(DEFAULT_MAX_POINTS))
            .andExpect(jsonPath("$.deadline").value(sameInstant(DEFAULT_DEADLINE)));
    }

    @Test
    void getNonExistingAssessment() throws Exception {
        // Get the assessment
        restAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment
        Assessment updatedAssessment = assessmentRepository.findById(assessment.getId()).orElseThrow();
        updatedAssessment
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .languageFramework(UPDATED_LANGUAGE_FRAMEWORK)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .validationCriteria(UPDATED_VALIDATION_CRITERIA)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS)
            .deadline(UPDATED_DEADLINE);
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(updatedAssessment);

        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAssessment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssessment.getLanguageFramework()).isEqualTo(UPDATED_LANGUAGE_FRAMEWORK);
        assertThat(testAssessment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssessment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssessment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssessment.getValidationCriteria()).isEqualTo(UPDATED_VALIDATION_CRITERIA);
        assertThat(testAssessment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssessment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
        assertThat(testAssessment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void putNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assessmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment using partial update
        Assessment partialUpdatedAssessment = new Assessment();
        partialUpdatedAssessment.setId(assessment.getId());

        partialUpdatedAssessment
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .validationCriteria(UPDATED_VALIDATION_CRITERIA)
            .question(UPDATED_QUESTION)
            .deadline(UPDATED_DEADLINE);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAssessment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssessment.getLanguageFramework()).isEqualTo(DEFAULT_LANGUAGE_FRAMEWORK);
        assertThat(testAssessment.getDifficultyLevel()).isEqualTo(DEFAULT_DIFFICULTY_LEVEL);
        assertThat(testAssessment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssessment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssessment.getValidationCriteria()).isEqualTo(UPDATED_VALIDATION_CRITERIA);
        assertThat(testAssessment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssessment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);
        assertThat(testAssessment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void fullUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();

        // Update the assessment using partial update
        Assessment partialUpdatedAssessment = new Assessment();
        partialUpdatedAssessment.setId(assessment.getId());

        partialUpdatedAssessment
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .languageFramework(UPDATED_LANGUAGE_FRAMEWORK)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .validationCriteria(UPDATED_VALIDATION_CRITERIA)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS)
            .deadline(UPDATED_DEADLINE);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssessment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
        Assessment testAssessment = assessmentList.get(assessmentList.size() - 1);
        assertThat(testAssessment.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAssessment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssessment.getLanguageFramework()).isEqualTo(UPDATED_LANGUAGE_FRAMEWORK);
        assertThat(testAssessment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssessment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssessment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssessment.getValidationCriteria()).isEqualTo(UPDATED_VALIDATION_CRITERIA);
        assertThat(testAssessment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssessment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
        assertThat(testAssessment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void patchNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assessmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assessmentRepository.findAll().size();
        assessment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssessmentDTO assessmentDTO = assessmentMapper.toDto(assessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAssessment() throws Exception {
        // Initialize the database
        assessmentRepository.save(assessment);

        int databaseSizeBeforeDelete = assessmentRepository.findAll().size();

        // Delete the assessment
        restAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, assessment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assessment> assessmentList = assessmentRepository.findAll();
        assertThat(assessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
