package in.upcode.cat.service;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.repository.AssignmentRepository;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.mapper.AssignmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Assignment}.
 */
@Service
public class AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentService.class);

    private final AssignmentRepository assignmentRepository;

    private final AssignmentMapper assignmentMapper;

    public AssignmentService(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    /**
     * Save a assessment.
     *
     * @param assignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        log.debug("Request to save Assessment : {}", assignmentDTO);
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    /**
     * Update a assessment.
     *
     * @param assignmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AssignmentDTO update(AssignmentDTO assignmentDTO) {
        log.debug("Request to update Assessment : {}", assignmentDTO);
        Assignment assignment = assignmentMapper.toEntity(assignmentDTO);
        assignment = assignmentRepository.save(assignment);
        return assignmentMapper.toDto(assignment);
    }

    /**
     * Partially update a assessment.
     *
     * @param assignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO) {
        log.debug("Request to partially update Assessment : {}", assignmentDTO);

        return assignmentRepository
            .findById(assignmentDTO.getId())
            .map(existingAssessment -> {
                assignmentMapper.partialUpdate(existingAssessment, assignmentDTO);

                return existingAssessment;
            })
            .map(assignmentRepository::save)
            .map(assignmentMapper::toDto);
    }

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assessments");
        return assignmentRepository.findAll(pageable).map(assignmentMapper::toDto);
    }

    /**
     * Retrieve assessments by type.
     *
     * @param type     the type of assessment to search for.
     * @param pageable the pagination information.
     * @return a page of assessments with the specified type.
     */
    public Page<AssignmentDTO> findAssessmentsByType(String type, Pageable pageable) {
        return assignmentRepository.findByTypeRegexIgnoreCase(type, pageable).map(assignmentMapper::toDto);
    }

    /**
     * Get one assessment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AssignmentDTO> findOne(String id) {
        log.debug("Request to get Assessment : {}", id);
        return assignmentRepository.findById(id).map(assignmentMapper::toDto);
    }

    /**
     * Delete the assessment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Assessment : {}", id);
        assignmentRepository.deleteById(id);
    }
}
