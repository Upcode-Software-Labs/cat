package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.domain.enumeration.AssignmentStatus;
import in.upcode.cat.repository.UserAssignmentRepository;
import in.upcode.cat.service.dto.UserAssignmentDTO;
import in.upcode.cat.service.mapper.UserAssignmentMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link UserAssignmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAssignmentResourceIT {

    private static final AssignmentStatus DEFAULT_STATUS = AssignmentStatus.ASSIGNED;
    private static final AssignmentStatus UPDATED_STATUS = AssignmentStatus.COMPLETED;

    private static final Instant DEFAULT_ASSIGNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserAssignmentRepository userAssignmentRepository;

    @Autowired
    private UserAssignmentMapper userAssignmentMapper;

    @Autowired
    private MockMvc restUserAssessmentMockMvc;

    private UserAssignment userAssignment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssignment createEntity() {
        UserAssignment userAssignment = new UserAssignment()
            .status(DEFAULT_STATUS)
            .assignedAt(DEFAULT_ASSIGNED_AT)
            .deadline(DEFAULT_DEADLINE);
        return userAssignment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssignment createUpdatedEntity() {
        UserAssignment userAssignment = new UserAssignment()
            .status(UPDATED_STATUS)
            .assignedAt(UPDATED_ASSIGNED_AT)
            .deadline(UPDATED_DEADLINE);
        return userAssignment;
    }

    @BeforeEach
    public void initTest() {
        userAssignmentRepository.deleteAll();
        userAssignment = createEntity();
    }

    @Test
    void createUserAssessment() throws Exception {
        int databaseSizeBeforeCreate = userAssignmentRepository.findAll().size();
        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);
        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeCreate + 1);
        UserAssignment testUserAssignment = userAssignmentList.get(userAssignmentList.size() - 1);
        assertThat(testUserAssignment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserAssignment.getAssignedAt()).isEqualTo(DEFAULT_ASSIGNED_AT);
        assertThat(testUserAssignment.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    void createUserAssessmentWithExistingId() throws Exception {
        // Create the UserAssessment with an existing ID
        userAssignment.setId("existing_id");
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        int databaseSizeBeforeCreate = userAssignmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssignmentRepository.findAll().size();
        // set the field null
        userAssignment.setStatus(null);

        // Create the UserAssessment, which fails.
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAssignedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssignmentRepository.findAll().size();
        // set the field null
        userAssignment.setAssignedAt(null);

        // Create the UserAssessment, which fails.
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllUserAssessments() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        // Get all the userAssessmentList
        restUserAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAssignment.getId())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].assignedAt").value(hasItem(DEFAULT_ASSIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())));
    }

    @Test
    void getUserAssessment() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        // Get the userAssessment
        restUserAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, userAssignment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAssignment.getId()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.assignedAt").value(DEFAULT_ASSIGNED_AT.toString()))
            .andExpect(jsonPath("$.deadline").value(DEFAULT_DEADLINE.toString()));
    }

    @Test
    void getNonExistingUserAssessment() throws Exception {
        // Get the userAssessment
        restUserAssessmentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingUserAssessment() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();

        // Update the userAssessment
        UserAssignment updatedUserAssignment = userAssignmentRepository.findById(userAssignment.getId()).orElseThrow();
        updatedUserAssignment.status(UPDATED_STATUS).assignedAt(UPDATED_ASSIGNED_AT).deadline(UPDATED_DEADLINE);
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(updatedUserAssignment);

        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssignment testUserAssignment = userAssignmentList.get(userAssignmentList.size() - 1);
        assertThat(testUserAssignment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserAssignment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssignment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void putNonExistingUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAssignmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserAssessmentWithPatch() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();

        // Update the userAssessment using partial update
        UserAssignment partialUpdatedUserAssignment = new UserAssignment();
        partialUpdatedUserAssignment.setId(userAssignment.getId());

        partialUpdatedUserAssignment.assignedAt(UPDATED_ASSIGNED_AT);

        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAssignment))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssignment testUserAssignment = userAssignmentList.get(userAssignmentList.size() - 1);
        assertThat(testUserAssignment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserAssignment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssignment.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    void fullUpdateUserAssessmentWithPatch() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();

        // Update the userAssessment using partial update
        UserAssignment partialUpdatedUserAssignment = new UserAssignment();
        partialUpdatedUserAssignment.setId(userAssignment.getId());

        partialUpdatedUserAssignment.status(UPDATED_STATUS).assignedAt(UPDATED_ASSIGNED_AT).deadline(UPDATED_DEADLINE);

        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAssignment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAssignment))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssignment testUserAssignment = userAssignmentList.get(userAssignmentList.size() - 1);
        assertThat(testUserAssignment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserAssignment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssignment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void patchNonExistingUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAssignmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssignmentRepository.findAll().size();
        userAssignment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssignmentDTO userAssignmentDTO = userAssignmentMapper.toDto(userAssignment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssignmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAssessment in the database
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserAssessment() throws Exception {
        // Initialize the database
        userAssignmentRepository.save(userAssignment);

        int databaseSizeBeforeDelete = userAssignmentRepository.findAll().size();

        // Delete the userAssessment
        restUserAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAssignment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAssignment> userAssignmentList = userAssignmentRepository.findAll();
        assertThat(userAssignmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
