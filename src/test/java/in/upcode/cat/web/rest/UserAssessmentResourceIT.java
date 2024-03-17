package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.UserAssessment;
import in.upcode.cat.domain.enumeration.AssessmentStatus;
import in.upcode.cat.repository.UserAssessmentRepository;
import in.upcode.cat.service.dto.UserAssessmentDTO;
import in.upcode.cat.service.mapper.UserAssessmentMapper;
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
 * Integration tests for the {@link UserAssessmentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserAssessmentResourceIT {

    private static final AssessmentStatus DEFAULT_STATUS = AssessmentStatus.ASSIGNED;
    private static final AssessmentStatus UPDATED_STATUS = AssessmentStatus.COMPLETED;

    private static final Instant DEFAULT_ASSIGNED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ASSIGNED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DEADLINE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DEADLINE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/user-assessments";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private UserAssessmentRepository userAssessmentRepository;

    @Autowired
    private UserAssessmentMapper userAssessmentMapper;

    @Autowired
    private MockMvc restUserAssessmentMockMvc;

    private UserAssessment userAssessment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssessment createEntity() {
        UserAssessment userAssessment = new UserAssessment()
            .status(DEFAULT_STATUS)
            .assignedAt(DEFAULT_ASSIGNED_AT)
            .deadline(DEFAULT_DEADLINE);
        return userAssessment;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserAssessment createUpdatedEntity() {
        UserAssessment userAssessment = new UserAssessment()
            .status(UPDATED_STATUS)
            .assignedAt(UPDATED_ASSIGNED_AT)
            .deadline(UPDATED_DEADLINE);
        return userAssessment;
    }

    @BeforeEach
    public void initTest() {
        userAssessmentRepository.deleteAll();
        userAssessment = createEntity();
    }

    @Test
    void createUserAssessment() throws Exception {
        int databaseSizeBeforeCreate = userAssessmentRepository.findAll().size();
        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);
        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeCreate + 1);
        UserAssessment testUserAssessment = userAssessmentList.get(userAssessmentList.size() - 1);
        assertThat(testUserAssessment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserAssessment.getAssignedAt()).isEqualTo(DEFAULT_ASSIGNED_AT);
        assertThat(testUserAssessment.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    void createUserAssessmentWithExistingId() throws Exception {
        // Create the UserAssessment with an existing ID
        userAssessment.setId("existing_id");
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        int databaseSizeBeforeCreate = userAssessmentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssessmentRepository.findAll().size();
        // set the field null
        userAssessment.setStatus(null);

        // Create the UserAssessment, which fails.
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkAssignedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = userAssessmentRepository.findAll().size();
        // set the field null
        userAssessment.setAssignedAt(null);

        // Create the UserAssessment, which fails.
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        restUserAssessmentMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllUserAssessments() throws Exception {
        // Initialize the database
        userAssessmentRepository.save(userAssessment);

        // Get all the userAssessmentList
        restUserAssessmentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAssessment.getId())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].assignedAt").value(hasItem(DEFAULT_ASSIGNED_AT.toString())))
            .andExpect(jsonPath("$.[*].deadline").value(hasItem(DEFAULT_DEADLINE.toString())));
    }

    @Test
    void getUserAssessment() throws Exception {
        // Initialize the database
        userAssessmentRepository.save(userAssessment);

        // Get the userAssessment
        restUserAssessmentMockMvc
            .perform(get(ENTITY_API_URL_ID, userAssessment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userAssessment.getId()))
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
        userAssessmentRepository.save(userAssessment);

        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();

        // Update the userAssessment
        UserAssessment updatedUserAssessment = userAssessmentRepository.findById(userAssessment.getId()).orElseThrow();
        updatedUserAssessment.status(UPDATED_STATUS).assignedAt(UPDATED_ASSIGNED_AT).deadline(UPDATED_DEADLINE);
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(updatedUserAssessment);

        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAssessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssessment testUserAssessment = userAssessmentList.get(userAssessmentList.size() - 1);
        assertThat(testUserAssessment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserAssessment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssessment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void putNonExistingUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userAssessmentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateUserAssessmentWithPatch() throws Exception {
        // Initialize the database
        userAssessmentRepository.save(userAssessment);

        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();

        // Update the userAssessment using partial update
        UserAssessment partialUpdatedUserAssessment = new UserAssessment();
        partialUpdatedUserAssessment.setId(userAssessment.getId());

        partialUpdatedUserAssessment.assignedAt(UPDATED_ASSIGNED_AT);

        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAssessment))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssessment testUserAssessment = userAssessmentList.get(userAssessmentList.size() - 1);
        assertThat(testUserAssessment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserAssessment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssessment.getDeadline()).isEqualTo(DEFAULT_DEADLINE);
    }

    @Test
    void fullUpdateUserAssessmentWithPatch() throws Exception {
        // Initialize the database
        userAssessmentRepository.save(userAssessment);

        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();

        // Update the userAssessment using partial update
        UserAssessment partialUpdatedUserAssessment = new UserAssessment();
        partialUpdatedUserAssessment.setId(userAssessment.getId());

        partialUpdatedUserAssessment.status(UPDATED_STATUS).assignedAt(UPDATED_ASSIGNED_AT).deadline(UPDATED_DEADLINE);

        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserAssessment.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserAssessment))
            )
            .andExpect(status().isOk());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
        UserAssessment testUserAssessment = userAssessmentList.get(userAssessmentList.size() - 1);
        assertThat(testUserAssessment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserAssessment.getAssignedAt()).isEqualTo(UPDATED_ASSIGNED_AT);
        assertThat(testUserAssessment.getDeadline()).isEqualTo(UPDATED_DEADLINE);
    }

    @Test
    void patchNonExistingUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userAssessmentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamUserAssessment() throws Exception {
        int databaseSizeBeforeUpdate = userAssessmentRepository.findAll().size();
        userAssessment.setId(UUID.randomUUID().toString());

        // Create the UserAssessment
        UserAssessmentDTO userAssessmentDTO = userAssessmentMapper.toDto(userAssessment);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserAssessmentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userAssessmentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserAssessment in the database
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteUserAssessment() throws Exception {
        // Initialize the database
        userAssessmentRepository.save(userAssessment);

        int databaseSizeBeforeDelete = userAssessmentRepository.findAll().size();

        // Delete the userAssessment
        restUserAssessmentMockMvc
            .perform(delete(ENTITY_API_URL_ID, userAssessment.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserAssessment> userAssessmentList = userAssessmentRepository.findAll();
        assertThat(userAssessmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
