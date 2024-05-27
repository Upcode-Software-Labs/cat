package in.upcode.cat.service;

import in.upcode.cat.domain.UserAssessment;
import in.upcode.cat.repository.UserAssessmentRepository;
import in.upcode.cat.service.dto.UserAssessmentDTO;
import in.upcode.cat.service.mapper.UserAssessmentMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link in.upcode.cat.domain.UserAssessment}.
 */
@Service
public class UserAssessmentService {

    private final Logger log = LoggerFactory.getLogger(UserAssessmentService.class);

    private final UserAssessmentRepository userAssessmentRepository;

    private final UserAssessmentMapper userAssessmentMapper;

    public UserAssessmentService(UserAssessmentRepository userAssessmentRepository, UserAssessmentMapper userAssessmentMapper) {
        this.userAssessmentRepository = userAssessmentRepository;
        this.userAssessmentMapper = userAssessmentMapper;
    }

    /**
     * Save a userAssessment.
     *
     * @param userAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssessmentDTO save(UserAssessmentDTO userAssessmentDTO) {
        log.debug("Request to save UserAssessment : {}", userAssessmentDTO);
        UserAssessment userAssessment = userAssessmentMapper.toEntity(userAssessmentDTO);
        userAssessment = userAssessmentRepository.save(userAssessment);
        return userAssessmentMapper.toDto(userAssessment);
    }

    /**
     * Update a userAssessment.
     *
     * @param userAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    public UserAssessmentDTO update(UserAssessmentDTO userAssessmentDTO) {
        log.debug("Request to update UserAssessment : {}", userAssessmentDTO);
        UserAssessment userAssessment = userAssessmentMapper.toEntity(userAssessmentDTO);
        userAssessment = userAssessmentRepository.save(userAssessment);
        return userAssessmentMapper.toDto(userAssessment);
    }

    /**
     * Partially update a userAssessment.
     *
     * @param userAssessmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<UserAssessmentDTO> partialUpdate(UserAssessmentDTO userAssessmentDTO) {
        log.debug("Request to partially update UserAssessment : {}", userAssessmentDTO);

        return userAssessmentRepository
            .findById(userAssessmentDTO.getId())
            .map(existingUserAssessment -> {
                userAssessmentMapper.partialUpdate(existingUserAssessment, userAssessmentDTO);

                return existingUserAssessment;
            })
            .map(userAssessmentRepository::save)
            .map(userAssessmentMapper::toDto);
    }

    /**
     * Get all the userAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAssessments");
        return userAssessmentRepository.findAll(pageable).map(userAssessmentMapper::toDto);
    }

    /**
     * Get all the userAssessments.
     *
     *  @param status the pagination information.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<UserAssessmentDTO> findBySearch(String status, Pageable pageable) {
        log.debug("Request to get UserAssessments based on status");
        return userAssessmentRepository.findByStatus(status, pageable).map(userAssessmentMapper::toDto);
    }

    /**
     * Get one userAssessment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<UserAssessmentDTO> findOne(String id) {
        log.debug("Request to get UserAssessment : {}", id);
        return userAssessmentRepository.findById(id).map(userAssessmentMapper::toDto);
    }

    /**
     * Delete the userAssessment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete UserAssessment : {}", id);
        userAssessmentRepository.deleteById(id);
    }
}
