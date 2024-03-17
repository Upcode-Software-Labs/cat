package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.Question;
import in.upcode.cat.repository.QuestionRepository;
import in.upcode.cat.service.dto.QuestionDTO;
import in.upcode.cat.service.mapper.QuestionMapper;
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
 * Integration tests for the {@link QuestionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class QuestionResourceIT {

    private static final String DEFAULT_QUESTION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_SNIPPET = "AAAAAAAAAA";
    private static final String UPDATED_CODE_SNIPPET = "BBBBBBBBBB";

    private static final String DEFAULT_RESOURCES = "AAAAAAAAAA";
    private static final String UPDATED_RESOURCES = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINTS = 1;
    private static final Integer UPDATED_POINTS = 2;

    private static final String ENTITY_API_URL = "/api/questions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private MockMvc restQuestionMockMvc;

    private Question question;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createEntity() {
        Question question = new Question()
            .questionText(DEFAULT_QUESTION_TEXT)
            .codeSnippet(DEFAULT_CODE_SNIPPET)
            .resources(DEFAULT_RESOURCES)
            .points(DEFAULT_POINTS);
        return question;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Question createUpdatedEntity() {
        Question question = new Question()
            .questionText(UPDATED_QUESTION_TEXT)
            .codeSnippet(UPDATED_CODE_SNIPPET)
            .resources(UPDATED_RESOURCES)
            .points(UPDATED_POINTS);
        return question;
    }

    @BeforeEach
    public void initTest() {
        questionRepository.deleteAll();
        question = createEntity();
    }

    @Test
    void createQuestion() throws Exception {
        int databaseSizeBeforeCreate = questionRepository.findAll().size();
        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);
        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isCreated());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate + 1);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestionText()).isEqualTo(DEFAULT_QUESTION_TEXT);
        assertThat(testQuestion.getCodeSnippet()).isEqualTo(DEFAULT_CODE_SNIPPET);
        assertThat(testQuestion.getResources()).isEqualTo(DEFAULT_RESOURCES);
        assertThat(testQuestion.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    void createQuestionWithExistingId() throws Exception {
        // Create the Question with an existing ID
        question.setId("existing_id");
        QuestionDTO questionDTO = questionMapper.toDto(question);

        int databaseSizeBeforeCreate = questionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void getAllQuestions() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        // Get all the questionList
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(question.getId())))
            .andExpect(jsonPath("$.[*].questionText").value(hasItem(DEFAULT_QUESTION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].codeSnippet").value(hasItem(DEFAULT_CODE_SNIPPET.toString())))
            .andExpect(jsonPath("$.[*].resources").value(hasItem(DEFAULT_RESOURCES)))
            .andExpect(jsonPath("$.[*].points").value(hasItem(DEFAULT_POINTS)));
    }

    @Test
    void getQuestion() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        // Get the question
        restQuestionMockMvc
            .perform(get(ENTITY_API_URL_ID, question.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(question.getId()))
            .andExpect(jsonPath("$.questionText").value(DEFAULT_QUESTION_TEXT.toString()))
            .andExpect(jsonPath("$.codeSnippet").value(DEFAULT_CODE_SNIPPET.toString()))
            .andExpect(jsonPath("$.resources").value(DEFAULT_RESOURCES))
            .andExpect(jsonPath("$.points").value(DEFAULT_POINTS));
    }

    @Test
    void getNonExistingQuestion() throws Exception {
        // Get the question
        restQuestionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingQuestion() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question
        Question updatedQuestion = questionRepository.findById(question.getId()).orElseThrow();
        updatedQuestion
            .questionText(UPDATED_QUESTION_TEXT)
            .codeSnippet(UPDATED_CODE_SNIPPET)
            .resources(UPDATED_RESOURCES)
            .points(UPDATED_POINTS);
        QuestionDTO questionDTO = questionMapper.toDto(updatedQuestion);

        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQuestion.getCodeSnippet()).isEqualTo(UPDATED_CODE_SNIPPET);
        assertThat(testQuestion.getResources()).isEqualTo(UPDATED_RESOURCES);
        assertThat(testQuestion.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    void putNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(questionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion.questionText(UPDATED_QUESTION_TEXT).resources(UPDATED_RESOURCES);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQuestion.getCodeSnippet()).isEqualTo(DEFAULT_CODE_SNIPPET);
        assertThat(testQuestion.getResources()).isEqualTo(UPDATED_RESOURCES);
        assertThat(testQuestion.getPoints()).isEqualTo(DEFAULT_POINTS);
    }

    @Test
    void fullUpdateQuestionWithPatch() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        int databaseSizeBeforeUpdate = questionRepository.findAll().size();

        // Update the question using partial update
        Question partialUpdatedQuestion = new Question();
        partialUpdatedQuestion.setId(question.getId());

        partialUpdatedQuestion
            .questionText(UPDATED_QUESTION_TEXT)
            .codeSnippet(UPDATED_CODE_SNIPPET)
            .resources(UPDATED_RESOURCES)
            .points(UPDATED_POINTS);

        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedQuestion.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedQuestion))
            )
            .andExpect(status().isOk());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
        Question testQuestion = questionList.get(questionList.size() - 1);
        assertThat(testQuestion.getQuestionText()).isEqualTo(UPDATED_QUESTION_TEXT);
        assertThat(testQuestion.getCodeSnippet()).isEqualTo(UPDATED_CODE_SNIPPET);
        assertThat(testQuestion.getResources()).isEqualTo(UPDATED_RESOURCES);
        assertThat(testQuestion.getPoints()).isEqualTo(UPDATED_POINTS);
    }

    @Test
    void patchNonExistingQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, questionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamQuestion() throws Exception {
        int databaseSizeBeforeUpdate = questionRepository.findAll().size();
        question.setId(UUID.randomUUID().toString());

        // Create the Question
        QuestionDTO questionDTO = questionMapper.toDto(question);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restQuestionMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(questionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Question in the database
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteQuestion() throws Exception {
        // Initialize the database
        questionRepository.save(question);

        int databaseSizeBeforeDelete = questionRepository.findAll().size();

        // Delete the question
        restQuestionMockMvc
            .perform(delete(ENTITY_API_URL_ID, question.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Question> questionList = questionRepository.findAll();
        assertThat(questionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
