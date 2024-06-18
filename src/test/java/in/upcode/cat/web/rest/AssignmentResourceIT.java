package in.upcode.cat.web.rest;

import static in.upcode.cat.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.Assignment;
import in.upcode.cat.domain.Category;
import in.upcode.cat.repository.AssignmentRepository;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.mapper.AssignmentMapper;
import java.time.Instant;
import java.time.LocalTime;
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

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = new byte[0];
    private static final byte[] UPDATED_IMAGE = new byte[0];

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_URL_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_URL_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_TECHNOLOGY = "AAAAAAAAAA";
    private static final String UPDATED_TECHNOLOGY = "BBBBBBBBBB";

    private static final String DEFAULT_DIFFICULTY_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_DIFFICULTY_LEVEL = "BBBBBBBBBB";

    private static final LocalTime DEFAULT_TIME_LIMIT = LocalTime.now();
    private static final LocalTime UPDATED_TIME_LIMIT = LocalTime.now();

    private static final Category DEFAULT_TYPE = new Category();
    private static final Category UPDATED_TYPE = new Category();

    private static final String DEFAULT_EVALUATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EVALUATION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_POINTS = 1;
    private static final Integer UPDATED_MAX_POINTS = 2;

    private static final String ENTITY_API_URL = "/api/assignments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AssignmentRepository assignmentRepository;

    @Autowired
    private AssignmentMapper assignmentMapper;

    @Autowired
    private MockMvc restAssignmentMockMvc;

    private Assignment assignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Assignment createEntity() {
        Assignment assignment = new Assignment()
            .question(DEFAULT_QUESTION)
            .description(DEFAULT_DESCRIPTION)
            .image(DEFAULT_IMAGE)
            .url(DEFAULT_URL)
            .urlType(DEFAULT_URL_TYPE)
            .technology(DEFAULT_TECHNOLOGY)
            .difficultyLevel(DEFAULT_DIFFICULTY_LEVEL)
            .timeLimit(DEFAULT_TIME_LIMIT)
            .type(DEFAULT_TYPE)
            .evaluationType(DEFAULT_EVALUATION_TYPE)
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
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .url(UPDATED_URL)
            .urlType(UPDATED_URL_TYPE)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .maxPoints(UPDATED_MAX_POINTS);
        return assignment;
    }

    @BeforeEach
    public void initTest() {
        assignmentRepository.deleteAll();
        assignment = createEntity();
    }

    @Test
    void createAssignment() throws Exception {
        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();
        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);
        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isCreated());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate + 1);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testAssignment.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssignment.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testAssignment.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testAssignment.getUrlType()).isEqualTo(DEFAULT_URL_TYPE);
        assertThat(testAssignment.getTechnology()).isEqualTo(DEFAULT_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(DEFAULT_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(DEFAULT_TIME_LIMIT);
        assertThat(testAssignment.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testAssignment.getEvaluationType()).isEqualTo(DEFAULT_EVALUATION_TYPE);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(DEFAULT_MAX_POINTS);
    }

    @Test
    void createAssignmentWithExistingId() throws Exception {
        // Create the Assignment with an existing ID
        assignment.setId("existing_id");
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        int databaseSizeBeforeCreate = assignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkQuestionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setQuestion(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setDescription(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkImageIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setImage(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setUrl(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkUrlTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setUrlType(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTechnologyIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setTechnology(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkDifficultyLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setDifficultyLevel(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTimeLimitIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setTimeLimit(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setType(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkEvaluationTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = assignmentRepository.findAll().size();
        // set the field null
        assignment.setEvaluationType(null);

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
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

        // Create the Assignment, which fails.
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        restAssignmentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isBadRequest());

        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAssignments() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        // Get all the assignmentList
        restAssignmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assignment.getId())))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].urlType").value(hasItem(DEFAULT_URL_TYPE)))
            .andExpect(jsonPath("$.[*].technology").value(hasItem(DEFAULT_TECHNOLOGY)))
            .andExpect(jsonPath("$.[*].difficultyLevel").value(hasItem(DEFAULT_DIFFICULTY_LEVEL)))
            .andExpect(jsonPath("$.[*].timeLimit").value(hasItem(DEFAULT_TIME_LIMIT)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].evaluationType").value(hasItem(DEFAULT_EVALUATION_TYPE)))
            .andExpect(jsonPath("$.[*].maxPoints").value(hasItem(DEFAULT_MAX_POINTS)));
    }

    @Test
    void getAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        // Get the assignment
        restAssignmentMockMvc
            .perform(get(ENTITY_API_URL_ID, assignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assignment.getId()))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.urlType").value(DEFAULT_URL_TYPE))
            .andExpect(jsonPath("$.technology").value(DEFAULT_TECHNOLOGY))
            .andExpect(jsonPath("$.difficultyLevel").value(DEFAULT_DIFFICULTY_LEVEL))
            .andExpect(jsonPath("$.timeLimit").value(DEFAULT_TIME_LIMIT))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.evaluationType").value(DEFAULT_EVALUATION_TYPE))
            .andExpect(jsonPath("$.maxPoints").value(DEFAULT_MAX_POINTS));
    }

    @Test
    void getNonExistingAssignment() throws Exception {
        // Get the assignment
        restAssignmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment
        Assignment updatedAssignment = assignmentRepository.findById(assignment.getId()).orElseThrow();
        updatedAssignment
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .url(UPDATED_URL)
            .urlType(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .maxPoints(UPDATED_MAX_POINTS);
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(updatedAssignment);

        restAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAssignment.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAssignment.getUrlType()).isEqualTo(UPDATED_URL_TYPE);
        assertThat(testAssignment.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssignment.getEvaluationType()).isEqualTo(UPDATED_EVALUATION_TYPE);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
    }

    @Test
    void putNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(assignmentDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAssignmentWithPatch() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment using partial update
        Assignment partialUpdatedAssignment = new Assignment();
        partialUpdatedAssignment.setId(assignment.getId());

        partialUpdatedAssignment
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .url(UPDATED_URL)
            .urlType(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .maxPoints(UPDATED_MAX_POINTS);

        restAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignment))
            )
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAssignment.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAssignment.getUrlType()).isEqualTo(UPDATED_URL_TYPE);
        assertThat(testAssignment.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssignment.getEvaluationType()).isEqualTo(UPDATED_EVALUATION_TYPE);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
    }

    @Test
    void fullUpdateAssignmentWithPatch() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();

        // Update the assignment using partial update
        Assignment partialUpdatedAssignment = new Assignment();
        partialUpdatedAssignment.setId(assignment.getId());

        partialUpdatedAssignment
            .question(UPDATED_QUESTION)
            .description(UPDATED_DESCRIPTION)
            .image(UPDATED_IMAGE)
            .url(UPDATED_URL)
            .urlType(UPDATED_DESCRIPTION)
            .technology(UPDATED_TECHNOLOGY)
            .difficultyLevel(UPDATED_DIFFICULTY_LEVEL)
            .timeLimit(UPDATED_TIME_LIMIT)
            .type(UPDATED_TYPE)
            .evaluationType(UPDATED_EVALUATION_TYPE)
            .maxPoints(UPDATED_MAX_POINTS);

        restAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAssignment))
            )
            .andExpect(status().isOk());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
        Assignment testAssignment = assignmentList.get(assignmentList.size() - 1);
        assertThat(testAssignment.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testAssignment.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssignment.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testAssignment.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testAssignment.getUrlType()).isEqualTo(UPDATED_URL_TYPE);
        assertThat(testAssignment.getTechnology()).isEqualTo(UPDATED_TECHNOLOGY);
        assertThat(testAssignment.getDifficultyLevel()).isEqualTo(UPDATED_DIFFICULTY_LEVEL);
        assertThat(testAssignment.getTimeLimit()).isEqualTo(UPDATED_TIME_LIMIT);
        assertThat(testAssignment.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testAssignment.getEvaluationType()).isEqualTo(UPDATED_EVALUATION_TYPE);
        assertThat(testAssignment.getMaxPoints()).isEqualTo(UPDATED_MAX_POINTS);
    }

    @Test
    void patchNonExistingAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAssignment() throws Exception {
        int databaseSizeBeforeUpdate = assignmentRepository.findAll().size();
        assignment.setId(UUID.randomUUID().toString());

        // Create the Assignment
        AssignmentDTO assignmentDTO = assignmentMapper.toDto(assignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssignmentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(assignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Assignment in the database
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAssignment() throws Exception {
        // Initialize the database
        assignmentRepository.save(assignment);

        int databaseSizeBeforeDelete = assignmentRepository.findAll().size();

        // Delete the assignment
        restAssignmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, assignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Assignment> assignmentList = assignmentRepository.findAll();
        assertThat(assignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
