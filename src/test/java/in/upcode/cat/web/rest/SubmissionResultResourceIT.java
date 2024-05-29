package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.SubmissionResult;
import in.upcode.cat.repository.SubmissionResultRepository;
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
 * Integration tests for the {@link SubmissionResultResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubmissionResultResourceIT {

    private static final Integer DEFAULT_TOTAL_POINTS = 1;
    private static final Integer UPDATED_TOTAL_POINTS = 2;

    private static final String DEFAULT_DETAILED_RESULTS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILED_RESULTS = "BBBBBBBBBB";

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/submission-results";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SubmissionResultRepository submissionResultRepository;

    @Autowired
    private SubmissionResultMapper submissionResultMapper;

    @Autowired
    private MockMvc restSubmissionResultMockMvc;

    private SubmissionResult submissionResult;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubmissionResult createEntity() {
        SubmissionResult submissionResult = new SubmissionResult()
            .totalPoints(DEFAULT_TOTAL_POINTS)
            .detailedResults(DEFAULT_DETAILED_RESULTS)
            .feedback(DEFAULT_FEEDBACK);
        return submissionResult;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SubmissionResult createUpdatedEntity() {
        SubmissionResult submissionResult = new SubmissionResult()
            .totalPoints(UPDATED_TOTAL_POINTS)
            .detailedResults(UPDATED_DETAILED_RESULTS)
            .feedback(UPDATED_FEEDBACK);
        return submissionResult;
    }

    @BeforeEach
    public void initTest() {
        submissionResultRepository.deleteAll();
        submissionResult = createEntity();
    }

    @Test
    void createSubmissionResult() throws Exception {
        int databaseSizeBeforeCreate = submissionResultRepository.findAll().size();
        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);
        restSubmissionResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isCreated());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeCreate + 1);
        SubmissionResult testSubmissionResult = submissionResultList.get(submissionResultList.size() - 1);
        assertThat(testSubmissionResult.getTotalPoints()).isEqualTo(DEFAULT_TOTAL_POINTS);
        assertThat(testSubmissionResult.getDetailedResults()).isEqualTo(DEFAULT_DETAILED_RESULTS);
        assertThat(testSubmissionResult.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
    }

    @Test
    void createSubmissionResultWithExistingId() throws Exception {
        // Create the SubmissionResult with an existing ID
        submissionResult.setId("existing_id");
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        int databaseSizeBeforeCreate = submissionResultRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubmissionResultMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllSubmissionResults() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        // Get all the submissionResultList
        restSubmissionResultMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submissionResult.getId())))
            .andExpect(jsonPath("$.[*].totalPoints").value(hasItem(DEFAULT_TOTAL_POINTS)))
            .andExpect(jsonPath("$.[*].detailedResults").value(hasItem(DEFAULT_DETAILED_RESULTS.toString())))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK.toString())));
    }

    @Test
    void getSubmissionResult() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        // Get the submissionResult
        restSubmissionResultMockMvc
            .perform(get(ENTITY_API_URL_ID, submissionResult.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(submissionResult.getId()))
            .andExpect(jsonPath("$.totalPoints").value(DEFAULT_TOTAL_POINTS))
            .andExpect(jsonPath("$.detailedResults").value(DEFAULT_DETAILED_RESULTS.toString()))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK.toString()));
    }

    @Test
    void getNonExistingSubmissionResult() throws Exception {
        // Get the submissionResult
        restSubmissionResultMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSubmissionResult() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();

        // Update the submissionResult
        SubmissionResult updatedSubmissionResult = submissionResultRepository.findById(submissionResult.getId()).orElseThrow();
        updatedSubmissionResult.totalPoints(UPDATED_TOTAL_POINTS).detailedResults(UPDATED_DETAILED_RESULTS).feedback(UPDATED_FEEDBACK);
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(updatedSubmissionResult);

        restSubmissionResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isOk());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
        SubmissionResult testSubmissionResult = submissionResultList.get(submissionResultList.size() - 1);
        assertThat(testSubmissionResult.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testSubmissionResult.getDetailedResults()).isEqualTo(UPDATED_DETAILED_RESULTS);
        assertThat(testSubmissionResult.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    void putNonExistingSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionResultDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubmissionResultWithPatch() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();

        // Update the submissionResult using partial update
        SubmissionResult partialUpdatedSubmissionResult = new SubmissionResult();
        partialUpdatedSubmissionResult.setId(submissionResult.getId());

        partialUpdatedSubmissionResult.totalPoints(UPDATED_TOTAL_POINTS).feedback(UPDATED_FEEDBACK);

        restSubmissionResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmissionResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubmissionResult))
            )
            .andExpect(status().isOk());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
        SubmissionResult testSubmissionResult = submissionResultList.get(submissionResultList.size() - 1);
        assertThat(testSubmissionResult.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testSubmissionResult.getDetailedResults()).isEqualTo(DEFAULT_DETAILED_RESULTS);
        assertThat(testSubmissionResult.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    void fullUpdateSubmissionResultWithPatch() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();

        // Update the submissionResult using partial update
        SubmissionResult partialUpdatedSubmissionResult = new SubmissionResult();
        partialUpdatedSubmissionResult.setId(submissionResult.getId());

        partialUpdatedSubmissionResult
            .totalPoints(UPDATED_TOTAL_POINTS)
            .detailedResults(UPDATED_DETAILED_RESULTS)
            .feedback(UPDATED_FEEDBACK);

        restSubmissionResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmissionResult.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubmissionResult))
            )
            .andExpect(status().isOk());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
        SubmissionResult testSubmissionResult = submissionResultList.get(submissionResultList.size() - 1);
        assertThat(testSubmissionResult.getTotalPoints()).isEqualTo(UPDATED_TOTAL_POINTS);
        assertThat(testSubmissionResult.getDetailedResults()).isEqualTo(UPDATED_DETAILED_RESULTS);
        assertThat(testSubmissionResult.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
    }

    @Test
    void patchNonExistingSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, submissionResultDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubmissionResult() throws Exception {
        int databaseSizeBeforeUpdate = submissionResultRepository.findAll().size();
        submissionResult.setId(UUID.randomUUID().toString());

        // Create the SubmissionResult
        SubmissionResultDTO submissionResultDTO = submissionResultMapper.toDto(submissionResult);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionResultMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(submissionResultDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the SubmissionResult in the database
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubmissionResult() throws Exception {
        // Initialize the database
        submissionResultRepository.save(submissionResult);

        int databaseSizeBeforeDelete = submissionResultRepository.findAll().size();

        // Delete the submissionResult
        restSubmissionResultMockMvc
            .perform(delete(ENTITY_API_URL_ID, submissionResult.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SubmissionResult> submissionResultList = submissionResultRepository.findAll();
        assertThat(submissionResultList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
