package in.upcode.cat.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import in.upcode.cat.IntegrationTest;
import in.upcode.cat.domain.AuditLog;
import in.upcode.cat.repository.AuditLogRepository;
import in.upcode.cat.service.dto.AuditLogDTO;
import in.upcode.cat.service.mapper.AuditLogMapper;
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
 * Integration tests for the {@link AuditLogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AuditLogResourceIT {

    private static final String DEFAULT_ACTION = "AAAAAAAAAA";
    private static final String UPDATED_ACTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_PERFORMED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PERFORMED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_DETAILS = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/audit-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private AuditLogMapper auditLogMapper;

    @Autowired
    private MockMvc restAuditLogMockMvc;

    private AuditLog auditLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditLog createEntity() {
        AuditLog auditLog = new AuditLog().action(DEFAULT_ACTION).performedAt(DEFAULT_PERFORMED_AT).details(DEFAULT_DETAILS);
        return auditLog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuditLog createUpdatedEntity() {
        AuditLog auditLog = new AuditLog().action(UPDATED_ACTION).performedAt(UPDATED_PERFORMED_AT).details(UPDATED_DETAILS);
        return auditLog;
    }

    @BeforeEach
    public void initTest() {
        auditLogRepository.deleteAll();
        auditLog = createEntity();
    }

    @Test
    void createAuditLog() throws Exception {
        int databaseSizeBeforeCreate = auditLogRepository.findAll().size();
        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);
        restAuditLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditLogDTO)))
            .andExpect(status().isCreated());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeCreate + 1);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
        assertThat(testAuditLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAuditLog.getPerformedAt()).isEqualTo(DEFAULT_PERFORMED_AT);
        assertThat(testAuditLog.getDetails()).isEqualTo(DEFAULT_DETAILS);
    }

    @Test
    void createAuditLogWithExistingId() throws Exception {
        // Create the AuditLog with an existing ID
        auditLog.setId("existing_id");
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        int databaseSizeBeforeCreate = auditLogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkActionIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogRepository.findAll().size();
        // set the field null
        auditLog.setAction(null);

        // Create the AuditLog, which fails.
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        restAuditLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditLogDTO)))
            .andExpect(status().isBadRequest());

        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPerformedAtIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditLogRepository.findAll().size();
        // set the field null
        auditLog.setPerformedAt(null);

        // Create the AuditLog, which fails.
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        restAuditLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditLogDTO)))
            .andExpect(status().isBadRequest());

        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllAuditLogs() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        // Get all the auditLogList
        restAuditLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditLog.getId())))
            .andExpect(jsonPath("$.[*].action").value(hasItem(DEFAULT_ACTION)))
            .andExpect(jsonPath("$.[*].performedAt").value(hasItem(DEFAULT_PERFORMED_AT.toString())))
            .andExpect(jsonPath("$.[*].details").value(hasItem(DEFAULT_DETAILS.toString())));
    }

    @Test
    void getAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        // Get the auditLog
        restAuditLogMockMvc
            .perform(get(ENTITY_API_URL_ID, auditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditLog.getId()))
            .andExpect(jsonPath("$.action").value(DEFAULT_ACTION))
            .andExpect(jsonPath("$.performedAt").value(DEFAULT_PERFORMED_AT.toString()))
            .andExpect(jsonPath("$.details").value(DEFAULT_DETAILS.toString()));
    }

    @Test
    void getNonExistingAuditLog() throws Exception {
        // Get the auditLog
        restAuditLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Update the auditLog
        AuditLog updatedAuditLog = auditLogRepository.findById(auditLog.getId()).orElseThrow();
        updatedAuditLog.action(UPDATED_ACTION).performedAt(UPDATED_PERFORMED_AT).details(UPDATED_DETAILS);
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(updatedAuditLog);

        restAuditLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
        assertThat(testAuditLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAuditLog.getPerformedAt()).isEqualTo(UPDATED_PERFORMED_AT);
        assertThat(testAuditLog.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    void putNonExistingAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdateAuditLogWithPatch() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Update the auditLog using partial update
        AuditLog partialUpdatedAuditLog = new AuditLog();
        partialUpdatedAuditLog.setId(auditLog.getId());

        partialUpdatedAuditLog.performedAt(UPDATED_PERFORMED_AT).details(UPDATED_DETAILS);

        restAuditLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditLog))
            )
            .andExpect(status().isOk());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
        assertThat(testAuditLog.getAction()).isEqualTo(DEFAULT_ACTION);
        assertThat(testAuditLog.getPerformedAt()).isEqualTo(UPDATED_PERFORMED_AT);
        assertThat(testAuditLog.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    void fullUpdateAuditLogWithPatch() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();

        // Update the auditLog using partial update
        AuditLog partialUpdatedAuditLog = new AuditLog();
        partialUpdatedAuditLog.setId(auditLog.getId());

        partialUpdatedAuditLog.action(UPDATED_ACTION).performedAt(UPDATED_PERFORMED_AT).details(UPDATED_DETAILS);

        restAuditLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditLog))
            )
            .andExpect(status().isOk());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
        AuditLog testAuditLog = auditLogList.get(auditLogList.size() - 1);
        assertThat(testAuditLog.getAction()).isEqualTo(UPDATED_ACTION);
        assertThat(testAuditLog.getPerformedAt()).isEqualTo(UPDATED_PERFORMED_AT);
        assertThat(testAuditLog.getDetails()).isEqualTo(UPDATED_DETAILS);
    }

    @Test
    void patchNonExistingAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamAuditLog() throws Exception {
        int databaseSizeBeforeUpdate = auditLogRepository.findAll().size();
        auditLog.setId(UUID.randomUUID().toString());

        // Create the AuditLog
        AuditLogDTO auditLogDTO = auditLogMapper.toDto(auditLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditLogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditLogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AuditLog in the database
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deleteAuditLog() throws Exception {
        // Initialize the database
        auditLogRepository.save(auditLog);

        int databaseSizeBeforeDelete = auditLogRepository.findAll().size();

        // Delete the auditLog
        restAuditLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AuditLog> auditLogList = auditLogRepository.findAll();
        assertThat(auditLogList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
