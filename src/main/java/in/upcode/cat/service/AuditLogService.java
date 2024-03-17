package in.upcode.cat.service;

import in.upcode.cat.domain.AuditLog;
import in.upcode.cat.repository.AuditLogRepository;
import in.upcode.cat.service.dto.AuditLogDTO;
import in.upcode.cat.service.mapper.AuditLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.AuditLog}.
 */
@Service
public class AuditLogService {

    private final Logger log = LoggerFactory.getLogger(AuditLogService.class);

    private final AuditLogRepository auditLogRepository;

    private final AuditLogMapper auditLogMapper;

    public AuditLogService(AuditLogRepository auditLogRepository, AuditLogMapper auditLogMapper) {
        this.auditLogRepository = auditLogRepository;
        this.auditLogMapper = auditLogMapper;
    }

    /**
     * Save a auditLog.
     *
     * @param auditLogDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditLogDTO save(AuditLogDTO auditLogDTO) {
        log.debug("Request to save AuditLog : {}", auditLogDTO);
        AuditLog auditLog = auditLogMapper.toEntity(auditLogDTO);
        auditLog = auditLogRepository.save(auditLog);
        return auditLogMapper.toDto(auditLog);
    }

    /**
     * Update a auditLog.
     *
     * @param auditLogDTO the entity to save.
     * @return the persisted entity.
     */
    public AuditLogDTO update(AuditLogDTO auditLogDTO) {
        log.debug("Request to update AuditLog : {}", auditLogDTO);
        AuditLog auditLog = auditLogMapper.toEntity(auditLogDTO);
        auditLog = auditLogRepository.save(auditLog);
        return auditLogMapper.toDto(auditLog);
    }

    /**
     * Partially update a auditLog.
     *
     * @param auditLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AuditLogDTO> partialUpdate(AuditLogDTO auditLogDTO) {
        log.debug("Request to partially update AuditLog : {}", auditLogDTO);

        return auditLogRepository
            .findById(auditLogDTO.getId())
            .map(existingAuditLog -> {
                auditLogMapper.partialUpdate(existingAuditLog, auditLogDTO);

                return existingAuditLog;
            })
            .map(auditLogRepository::save)
            .map(auditLogMapper::toDto);
    }

    /**
     * Get all the auditLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AuditLogDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuditLogs");
        return auditLogRepository.findAll(pageable).map(auditLogMapper::toDto);
    }

    /**
     * Get one auditLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AuditLogDTO> findOne(String id) {
        log.debug("Request to get AuditLog : {}", id);
        return auditLogRepository.findById(id).map(auditLogMapper::toDto);
    }

    /**
     * Delete the auditLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete AuditLog : {}", id);
        auditLogRepository.deleteById(id);
    }
}
