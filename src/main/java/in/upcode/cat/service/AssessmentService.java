package in.upcode.cat.service;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.repository.AssessmentRepository;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.mapper.AssessmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.Assessment}.
 */
@Service
public class AssessmentService {

    private final Logger log = LoggerFactory.getLogger(AssessmentService.class);

    private final AssessmentRepository assessmentRepository;

    private final AssessmentMapper assessmentMapper;

    public AssessmentService(AssessmentRepository assessmentRepository, AssessmentMapper assessmentMapper) {
        this.assessmentRepository = assessmentRepository;
        this.assessmentMapper = assessmentMapper;
    }

    /**
     * Save a assessment.
     *
     * @param assessmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AssessmentDTO save(AssessmentDTO assessmentDTO) {
        log.debug("Request to save Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Update a assessment.
     *
     * @param assessmentDTO the entity to save.
     * @return the persisted entity.
     */
    public AssessmentDTO update(AssessmentDTO assessmentDTO) {
        log.debug("Request to update Assessment : {}", assessmentDTO);
        Assessment assessment = assessmentMapper.toEntity(assessmentDTO);
        assessment = assessmentRepository.save(assessment);
        return assessmentMapper.toDto(assessment);
    }

    /**
     * Partially update a assessment.
     *
     * @param assessmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<AssessmentDTO> partialUpdate(AssessmentDTO assessmentDTO) {
        log.debug("Request to partially update Assessment : {}", assessmentDTO);

        return assessmentRepository
            .findById(assessmentDTO.getId())
            .map(existingAssessment -> {
                assessmentMapper.partialUpdate(existingAssessment, assessmentDTO);

                return existingAssessment;
            })
            .map(assessmentRepository::save)
            .map(assessmentMapper::toDto);
    }

    /**
     * Get all the assessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<AssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assessments");
        return assessmentRepository.findAll(pageable).map(assessmentMapper::toDto);
    }

    /**
     * Retrieve assessments by type.
     *
     * @param type     the type of assessment to search for.
     * @param pageable the pagination information.
     * @return a page of assessments with the specified type.
     */
    public Page<AssessmentDTO> findAssessmentsByType(String type, Pageable pageable) {
        return assessmentRepository.findByTypeRegexIgnoreCase(type, pageable).map(assessmentMapper::toDto);
    }

    /**
     * Get one assessment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<AssessmentDTO> findOne(String id) {
        log.debug("Request to get Assessment : {}", id);
        return assessmentRepository.findById(id).map(assessmentMapper::toDto);
    }

    /**
     * Delete the assessment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Assessment : {}", id);
        assessmentRepository.deleteById(id);
    }
}
