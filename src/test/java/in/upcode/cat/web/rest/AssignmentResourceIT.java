package in.upcode.cat.web.rest;

import static in.upcode.cat.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.Assignment;
import in.upcode.cat.repository.AssignmentRepository;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.mapper.AssignmentMapper;
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
 * Integration tests for the {@link AssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssignmentResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_DIFFICULTY_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_DIFFICULTY_LEVEL = "BBBBBBBBBB";

    private static final Integer DEFAULT_TIME_LIMIT = 1;
    private static final Integer UPDATED_TIME_LIMIT = 2;

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
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private MockMvc restAssessmentMockMvc;

    private Assignment assignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createEntity() {
        Assignment assignment = new Assignment()
            .description(DEFAULT_DESCRIPTION)
            .technology(DEFAULT_TECHNOLOGY)
            .difficultyLevel(DEFAULT_DIFFICULTY_LEVEL)
            .timeLimit(DEFAULT_TIME_LIMIT)
            .question(DEFAULT_QUESTION)
            .maxPoints(DEFAULT_MAX_POINTS);
        return assignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createUpdatedEntity() {
        Assignment assignment = new Assignment()
            .description(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS);
        return assignment;
    }

    @BeforeEach
    public void initTest() {
        assignmentRepository.deleteAll();
        assignment = createEntity();
    }

    @Test
    void createAssessment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();
        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssignment.getLTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(DEFAULT_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(DEFAULT_TIME_LIMIT);
        assertThat(testAssignment.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);
    }

    @Test
    void createAssessmentWithExistingId() throws Exception {
        // Create the Assessment with an existing ID
        assignment.setId("existing_id");
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setQuestion(null);

        // Create the Assessment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkMaxPointsIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setMaxPoints(null);

        // Create the Assessment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssessmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAssessments() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        // Get all the assessmentList
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].difficultyLevel").value(hasItem(DEFAULT_DIFFICULTY_LEVEL)))
            .andExpect(jsonPath("$.[*].timeLimit").value(hasItem(DEFAULT_TIME_LIMIT)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].maxPoints").value(hasItem(DEFAULT_MAX_POINTS)));
    }

    @Test
    void getAssessment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        // Get the assessment
        restAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY))
            .andExpect(jsonPath("$.difficultyLevel").value(DEFAULT_DIFFICULTY_LEVEL))
            .andExpect(jsonPath("$.timeLimit").value(DEFAULT_TIME_LIMIT))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.maxPoints").value(DEFAULT_MAX_POINTS));
    }

    @Test
    void getNonExistingAssessment() throws Exception {
        // Get the assessment
        restAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAssessment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assessment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).orElseThrow();
        updatedAssignment
            .description(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(updatedAssignment);

        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getLTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
    }

    @Test
    void putNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assessment using partial update
        Assignment partialUpdatedAssignment = new Assignment();
        partialUpdatedAssignment.setId(assignment.getId());

        partialUpdatedAssignment.description(UPDATED_DESCRIPTION).timeLimit(UPDATED_TIME_LIMIT).question(UPDATED_QUESTION);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getLTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(DEFAULT_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);
    }

    @Test
    void fullUpdateAssessmentWithPatch() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assessment using partial update
        Assignment partialUpdatedAssignment = new Assignment();
        partialUpdatedAssignment.setId(assignment.getId());

        partialUpdatedAssignment
            .description(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .question(UPDATED_QUESTION)
            .maxPoints(UPDATED_MAX_POINTS);

        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignment))
            )
            .andExpect(status().isOk());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getLTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
    }

    @Test
    void patchNonExistingAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAssessment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assessment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assessment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAssessment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assessment
        restAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, assignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
