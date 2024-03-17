package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.Submission;
import in.upcode.cat.repository.SubmissionRepository;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.service.mapper.SubmissionMapper;
import java.util.Base64;
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
 * Integration tests for the {@link SubmissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubmissionResourceIT {

    private static final String DEFAULT_GITHUB_URL = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_URL = "BBBBBBBBBB";

    private static final byte[] DEFAULT_SCREENSHOTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_SCREENSHOTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_SCREENSHOTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_SCREENSHOTS_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO_EXPLANATION = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_EXPLANATION = "BBBBBBBBBB";

    private static final String DEFAULT_TEXT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FEEDBACK = "AAAAAAAAAA";
    private static final String UPDATED_FEEDBACK = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS_SCORED = 1;
    private static final Integer UPDATED_POINTS_SCORED = 2;

    private static final String ENTITY_API_URL = "/api/submissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private MockMvc restSubmissionMockMvc;

    private Submission submission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Submission createEntity() {
        Submission submission = new Submission()
            .githubUrl(DEFAULT_GITHUB_URL)
            .screenshots(DEFAULT_SCREENSHOTS)
            .screenshotsContentType(DEFAULT_SCREENSHOTS_CONTENT_TYPE)
            .videoExplanation(DEFAULT_VIDEO_EXPLANATION)
            .textDescription(DEFAULT_TEXT_DESCRIPTION)
            .feedback(DEFAULT_FEEDBACK)
            .pointsScored(DEFAULT_POINTS_SCORED);
        return submission;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Submission createUpdatedEntity() {
        Submission submission = new Submission()
            .githubUrl(UPDATED_GITHUB_URL)
            .screenshots(UPDATED_SCREENSHOTS)
            .screenshotsContentType(UPDATED_SCREENSHOTS_CONTENT_TYPE)
            .videoExplanation(UPDATED_VIDEO_EXPLANATION)
            .textDescription(UPDATED_TEXT_DESCRIPTION)
            .feedback(UPDATED_FEEDBACK)
            .pointsScored(UPDATED_POINTS_SCORED);
        return submission;
    }

    @BeforeEach
    public void initTest() {
        submissionRepository.deleteAll();
        submission = createEntity();
    }

    @Test
    void createSubmission() throws Exception {
        int databaseSizeBeforeCreate = submissionRepository.findAll().size();
        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);
        restSubmissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionDTO)))
            .andExpect(status().isCreated());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeCreate + 1);
        Submission testSubmission = submissionList.get(submissionList.size() - 1);
        assertThat(testSubmission.getGithubUrl()).isEqualTo(DEFAULT_GITHUB_URL);
        assertThat(testSubmission.getScreenshots()).isEqualTo(DEFAULT_SCREENSHOTS);
        assertThat(testSubmission.getScreenshotsContentType()).isEqualTo(DEFAULT_SCREENSHOTS_CONTENT_TYPE);
        assertThat(testSubmission.getVideoExplanation()).isEqualTo(DEFAULT_VIDEO_EXPLANATION);
        assertThat(testSubmission.getTextDescription()).isEqualTo(DEFAULT_TEXT_DESCRIPTION);
        assertThat(testSubmission.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testSubmission.getPointsScored()).isEqualTo(DEFAULT_POINTS_SCORED);
    }

    @Test
    void createSubmissionWithExistingId() throws Exception {
        // Create the Submission with an existing ID
        submission.setId("existing_id");
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        int databaseSizeBeforeCreate = submissionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubmissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkGithubUrlIsRequired() throws Exception {
        int databaseSizeBeforeTest = submissionRepository.findAll().size();
        // set the field null
        submission.setGithubUrl(null);

        // Create the Submission, which fails.
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        restSubmissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionDTO)))
            .andExpect(status().isBadRequest());

        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllSubmissions() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        // Get all the submissionList
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submission.getId())))
            .andExpect(jsonPath("$.[*].githubUrl").value(hasItem(DEFAULT_GITHUB_URL)))
            .andExpect(jsonPath("$.[*].screenshotsContentType").value(hasItem(DEFAULT_SCREENSHOTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].screenshots").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_SCREENSHOTS))))
            .andExpect(jsonPath("$.[*].videoExplanation").value(hasItem(DEFAULT_VIDEO_EXPLANATION)))
            .andExpect(jsonPath("$.[*].textDescription").value(hasItem(DEFAULT_TEXT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].feedback").value(hasItem(DEFAULT_FEEDBACK.toString())))
            .andExpect(jsonPath("$.[*].pointsScored").value(hasItem(DEFAULT_POINTS_SCORED)));
    }

    @Test
    void getSubmission() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        // Get the submission
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL_ID, submission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(submission.getId()))
            .andExpect(jsonPath("$.githubUrl").value(DEFAULT_GITHUB_URL))
            .andExpect(jsonPath("$.screenshotsContentType").value(DEFAULT_SCREENSHOTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.screenshots").value(Base64.getEncoder().encodeToString(DEFAULT_SCREENSHOTS)))
            .andExpect(jsonPath("$.videoExplanation").value(DEFAULT_VIDEO_EXPLANATION))
            .andExpect(jsonPath("$.textDescription").value(DEFAULT_TEXT_DESCRIPTION))
            .andExpect(jsonPath("$.feedback").value(DEFAULT_FEEDBACK.toString()))
            .andExpect(jsonPath("$.pointsScored").value(DEFAULT_POINTS_SCORED));
    }

    @Test
    void getNonExistingSubmission() throws Exception {
        // Get the submission
        restSubmissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingSubmission() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();

        // Update the submission
        Submission updatedSubmission = submissionRepository.findById(submission.getId()).orElseThrow();
        updatedSubmission
            .githubUrl(UPDATED_GITHUB_URL)
            .screenshots(UPDATED_SCREENSHOTS)
            .screenshotsContentType(UPDATED_SCREENSHOTS_CONTENT_TYPE)
            .videoExplanation(UPDATED_VIDEO_EXPLANATION)
            .textDescription(UPDATED_TEXT_DESCRIPTION)
            .feedback(UPDATED_FEEDBACK)
            .pointsScored(UPDATED_POINTS_SCORED);
        SubmissionDTO submissionDTO = submissionMapper.toDto(updatedSubmission);

        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
        Submission testSubmission = submissionList.get(submissionList.size() - 1);
        assertThat(testSubmission.getGithubUrl()).isEqualTo(UPDATED_GITHUB_URL);
        assertThat(testSubmission.getScreenshots()).isEqualTo(UPDATED_SCREENSHOTS);
        assertThat(testSubmission.getScreenshotsContentType()).isEqualTo(UPDATED_SCREENSHOTS_CONTENT_TYPE);
        assertThat(testSubmission.getVideoExplanation()).isEqualTo(UPDATED_VIDEO_EXPLANATION);
        assertThat(testSubmission.getTextDescription()).isEqualTo(UPDATED_TEXT_DESCRIPTION);
        assertThat(testSubmission.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testSubmission.getPointsScored()).isEqualTo(UPDATED_POINTS_SCORED);
    }

    @Test
    void putNonExistingSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(submissionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateSubmissionWithPatch() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();

        // Update the submission using partial update
        Submission partialUpdatedSubmission = new Submission();
        partialUpdatedSubmission.setId(submission.getId());

        partialUpdatedSubmission
            .screenshots(UPDATED_SCREENSHOTS)
            .screenshotsContentType(UPDATED_SCREENSHOTS_CONTENT_TYPE)
            .videoExplanation(UPDATED_VIDEO_EXPLANATION)
            .pointsScored(UPDATED_POINTS_SCORED);

        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubmission))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
        Submission testSubmission = submissionList.get(submissionList.size() - 1);
        assertThat(testSubmission.getGithubUrl()).isEqualTo(DEFAULT_GITHUB_URL);
        assertThat(testSubmission.getScreenshots()).isEqualTo(UPDATED_SCREENSHOTS);
        assertThat(testSubmission.getScreenshotsContentType()).isEqualTo(UPDATED_SCREENSHOTS_CONTENT_TYPE);
        assertThat(testSubmission.getVideoExplanation()).isEqualTo(UPDATED_VIDEO_EXPLANATION);
        assertThat(testSubmission.getTextDescription()).isEqualTo(DEFAULT_TEXT_DESCRIPTION);
        assertThat(testSubmission.getFeedback()).isEqualTo(DEFAULT_FEEDBACK);
        assertThat(testSubmission.getPointsScored()).isEqualTo(UPDATED_POINTS_SCORED);
    }

    @Test
    void fullUpdateSubmissionWithPatch() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();

        // Update the submission using partial update
        Submission partialUpdatedSubmission = new Submission();
        partialUpdatedSubmission.setId(submission.getId());

        partialUpdatedSubmission
            .githubUrl(UPDATED_GITHUB_URL)
            .screenshots(UPDATED_SCREENSHOTS)
            .screenshotsContentType(UPDATED_SCREENSHOTS_CONTENT_TYPE)
            .videoExplanation(UPDATED_VIDEO_EXPLANATION)
            .textDescription(UPDATED_TEXT_DESCRIPTION)
            .feedback(UPDATED_FEEDBACK)
            .pointsScored(UPDATED_POINTS_SCORED);

        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSubmission))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
        Submission testSubmission = submissionList.get(submissionList.size() - 1);
        assertThat(testSubmission.getGithubUrl()).isEqualTo(UPDATED_GITHUB_URL);
        assertThat(testSubmission.getScreenshots()).isEqualTo(UPDATED_SCREENSHOTS);
        assertThat(testSubmission.getScreenshotsContentType()).isEqualTo(UPDATED_SCREENSHOTS_CONTENT_TYPE);
        assertThat(testSubmission.getVideoExplanation()).isEqualTo(UPDATED_VIDEO_EXPLANATION);
        assertThat(testSubmission.getTextDescription()).isEqualTo(UPDATED_TEXT_DESCRIPTION);
        assertThat(testSubmission.getFeedback()).isEqualTo(UPDATED_FEEDBACK);
        assertThat(testSubmission.getPointsScored()).isEqualTo(UPDATED_POINTS_SCORED);
    }

    @Test
    void patchNonExistingSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamSubmission() throws Exception {
        int databaseSizeBeforeUpdate = submissionRepository.findAll().size();
        submission.setId(UUID.randomUUID().toString());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(submissionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Submission in the database
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteSubmission() throws Exception {
        // Initialize the database
        submissionRepository.save(submission);

        int databaseSizeBeforeDelete = submissionRepository.findAll().size();

        // Delete the submission
        restSubmissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, submission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Submission> submissionList = submissionRepository.findAll();
        assertThat(submissionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
